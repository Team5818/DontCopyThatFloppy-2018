package org.rivierarobotics.pathfollowing;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.mathUtil.MathUtil;
import org.rivierarobotics.mathUtil.Vector2d;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.subsystems.DriveTrainSide;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class TrajectoryExecutor implements Runnable {

    private DriveTrain driveTrain;
    private boolean running = false;
    private boolean isFinished = false;
    private double timeout;
    private double endTime;
    private Trajectory leftTraj;
    private Trajectory rightTraj;
    private EncoderFollower leftFollow;
    private EncoderFollower rightFollow;
    private Trajectory master;
    private double dt;
    private Notifier runner;
    private Vector2d currentPos;
    private double currentHeading;
    private boolean reversed;
    private int directionMultiplier;
    private Object lock = new Object();

    public static final double DEFAULT_DT = .01;
    public static final double DEFAULT_MAX_VEL = 60;
    public static final double DEFAULT_MAX_ACCEL = 60;
    public static final double DEFAULT_MAX_JERK = 500;
    private static final double DEFAULT_TIMEOUT = Double.POSITIVE_INFINITY;

    private static final double KP = 0.15;
    private static final double KI = 0.0;
    private static final double KD = 0.0;
    private static final double KV = 0.01;
    private static final double KA = 0.0003;
    private static final double K_OFFSET = .07;
    private static final double K_HEADING = 0.01;

    public static final Trajectory.Config DEFAULT_CONFIG = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_LOW, DEFAULT_DT, DEFAULT_MAX_VEL, DEFAULT_MAX_ACCEL, DEFAULT_MAX_JERK);

    public TrajectoryExecutor(Waypoint[] waypoints, Trajectory.Config config, double time, boolean rev) {
        driveTrain = Robot.runningRobot.driveTrain;
        reversed = rev;
        directionMultiplier = reversed ? -1:1;
        DriverStation.reportError("starting generation...", false);
        master = Pathfinder.generate(waypoints, config);
        DriverStation.reportError("done!", false);
        TankModifier mod = new TankModifier(master).modify(RobotConstants.WHEEL_BASE_WIDTH);
        if(reversed) {
            leftTraj = mod.getRightTrajectory();
            rightTraj = mod.getLeftTrajectory();
        }else {
            leftTraj = mod.getLeftTrajectory();
            rightTraj = mod.getRightTrajectory();
        }
        currentPos = driveTrain.getDistance();
        dt = config.dt;

        leftFollow = new EncoderFollower(leftTraj);
        rightFollow = new EncoderFollower(rightTraj);
        leftFollow.configurePIDVA(KP, KI, KD, KV, KA);
        rightFollow.configurePIDVA(KP, KI, KD, KV, KA);
        leftFollow.configureEncoder(0, DriveTrainSide.ENCODER_CODES_PER_REV*4,
                RobotConstants.WHEEL_DIAMETER);
        rightFollow.configureEncoder(0, DriveTrainSide.ENCODER_CODES_PER_REV*4,
                RobotConstants.WHEEL_DIAMETER);
        timeout = time;
        runner = new Notifier(this);
    }

    public TrajectoryExecutor(Waypoint[] waypoints, double time, boolean rev) {
        this(waypoints, DEFAULT_CONFIG, time, rev);
    }

    public TrajectoryExecutor(Waypoint[] waypoints, boolean rev) {
        this(waypoints, DEFAULT_CONFIG, DEFAULT_TIMEOUT,rev);
    }

    @Override
    public void run() {
        double time = Timer.getFPGATimestamp();
        if (!leftFollow.isFinished() && !rightFollow.isFinished() && time < endTime) {
            currentPos = driveTrain.getDistance();
            currentHeading = driveTrain.getYaw();
            Segment seg = leftFollow.getSegment();
            double left = directionMultiplier*(leftFollow.calculate(directionMultiplier*(int) currentPos.getX()) + K_OFFSET * Math.signum(seg.velocity));
            double right = directionMultiplier*(rightFollow.calculate(directionMultiplier*(int) currentPos.getY()) + K_OFFSET * Math.signum(seg.velocity));
            double headDiff = MathUtil.wrapAngleRad(currentHeading - Pathfinder.r2d(seg.heading));
            driveTrain.setPowerLeftRight(left + K_HEADING * headDiff, right - K_HEADING * headDiff);
            Robot.runningRobot.logger.storeValue(new double[] { (currentPos.getX() + currentPos.getY()) / 2,
                    driveTrain.getAvgSideVelocity(), seg.position, seg.velocity, currentHeading - Pathfinder.r2d(seg.heading),time});
        } else {
            synchronized(lock) {
                isFinished = true;
                stop();
            }
        }
    }

    public void start() {
        synchronized(lock) {
            runner.startPeriodic(dt);
            driveTrain.resetEnc();
            endTime = Timer.getFPGATimestamp() + timeout;
            running = true;
            DriverStation.reportError("are we doing this?", false);
        }
    }

    public void stop() {
        synchronized(lock) {
            runner.stop();
            running = false;
            Robot.runningRobot.logger.flushToDisk();
        }
    }

    public boolean isRunning() {
        synchronized(lock) {
            return running;
        }
    }

    public boolean isFinished() {
        synchronized(lock) {
            return isFinished;
        }
    }
}

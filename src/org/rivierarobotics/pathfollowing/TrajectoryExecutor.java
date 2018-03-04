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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    private EncoderFollower dummy;
    private Trajectory master;
    private double dt;
    private Notifier runner;
    private Vector2d currentPos;
    private double currentHeading;

    public static final double DEFAULT_DT = .02;
    public static final double DEFAULT_MAX_VEL = 60;
    public static final double DEFAULT_MAX_ACCEL = 60;
    public static final double DEFAULT_MAX_JERK = 500;
    private static final double DEFAULT_TIMEOUT = Double.POSITIVE_INFINITY;

    private static final double KP = 0.15;
    private static final double KI = 0.0;
    private static final double KD = 0.01;
    private static final double KV = 0.01;
    private static final double KA = 0.0003;
    private static final double K_OFFSET = .07;
    private static final double K_HEADING = 0.0;

    public static final Trajectory.Config DEFAULT_CONFIG = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_LOW, DEFAULT_DT, DEFAULT_MAX_VEL, DEFAULT_MAX_ACCEL, DEFAULT_MAX_JERK);

    public TrajectoryExecutor(Waypoint[] waypoints, Trajectory.Config config, double time) {
        driveTrain = Robot.runningRobot.driveTrain;
        DriverStation.reportError("starting generation...", false);
        master = Pathfinder.generate(waypoints, config);
        DriverStation.reportError("done!", false);
        TankModifier mod = new TankModifier(master).modify(RobotConstants.WHEEL_BASE_WIDTH);
        leftTraj = mod.getLeftTrajectory();
        rightTraj = mod.getRightTrajectory();
        currentPos = driveTrain.getDistance();
        dt = config.dt;

        leftFollow = new EncoderFollower(leftTraj);
        rightFollow = new EncoderFollower(rightTraj);
        dummy = new EncoderFollower(rightTraj);
        leftFollow.configurePIDVA(KP, KI, KD, KV, KA);
        rightFollow.configurePIDVA(KP, KI, KD, KV, KA);
        dummy.configurePIDVA(1, 0, 0, 0, 0);
        leftFollow.configureEncoder((int) currentPos.getX(), DriveTrainSide.ENCODER_CODES_PER_REV*4,
                RobotConstants.WHEEL_DIAMETER);
        rightFollow.configureEncoder((int) currentPos.getY(), DriveTrainSide.ENCODER_CODES_PER_REV*4,
                RobotConstants.WHEEL_DIAMETER);
        dummy.configureEncoder((int) currentPos.getY(), DriveTrainSide.ENCODER_CODES_PER_REV*4,
                RobotConstants.WHEEL_DIAMETER);
        timeout = time;

        runner = new Notifier(this);
    }

    public TrajectoryExecutor(Waypoint[] waypoints, double time) {
        this(waypoints, DEFAULT_CONFIG, time);
    }

    public TrajectoryExecutor(Waypoint[] waypoints) {
        this(waypoints, DEFAULT_CONFIG, DEFAULT_TIMEOUT);
    }

    @Override
    public void run() {
        double time = Timer.getFPGATimestamp();
        if (!leftFollow.isFinished() && !rightFollow.isFinished() && time < endTime) {
            currentPos = driveTrain.getDistance();
            Segment seg = leftFollow.getSegment();
            double left = leftFollow.calculate((int) currentPos.getX()) + K_OFFSET * Math.signum(seg.velocity);
            double right = rightFollow.calculate((int) currentPos.getY()) + K_OFFSET * Math.signum(seg.velocity);
            double err = dummy.calculate((int) currentPos.getY());
            double headDiff = MathUtil.wrapAngleRad(currentHeading - seg.heading);
            driveTrain.setPowerLeftRight(left - K_HEADING * headDiff, right + K_HEADING * headDiff);
            Robot.runningRobot.logger.storeValue(new double[] { (currentPos.getX() + currentPos.getY()) / 2,
                    driveTrain.getAvgSideVelocity(), seg.position, seg.velocity, err,time});
        } else {
            isFinished = true;
            stop();
        }
    }

    public void start() {
        runner.startPeriodic(dt);
        endTime = Timer.getFPGATimestamp() + timeout;
        running = true;
        DriverStation.reportError("are we doing this?", false);
    }

    public void stop() {
        runner.stop();
        running = false;
        Robot.runningRobot.logger.flushToDisk();
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isFinished() {
        return isFinished;
    }
}

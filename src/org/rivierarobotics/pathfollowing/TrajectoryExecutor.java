package org.rivierarobotics.pathfollowing;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.subsystems.DriveTrainSide;
import org.rivierarobotics.util.MathUtil;
import org.rivierarobotics.util.Vector2d;
import edu.wpi.first.wpilibj.CircularBuffer;
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

    public static final int NUM_SAMPLES = 20;
    public static final double UNINITIALIZED_SENTINEL = -254;
    public static final double DEFAULT_DT = .01;
    public static final double DEFAULT_MAX_VEL = 80;
    public static final double DEFAULT_MAX_ACCEL = 60;
    public static final double DEFAULT_MAX_JERK = 500;
    public static final double DEFAULT_TIMEOUT = Double.POSITIVE_INFINITY;
    public static final double KP = 0.1;
    public static final double KI = 0.0;
    public static final double KD = 0.0;
    public static final double KV = 0.0086;
    public static final double KA = 0.0024;
    public static final double K_OFFSET = 0.045;
    public static final double K_HEADING = 0.03;

    public enum TrajectoryExecutionState {
        STATE_STABILIZING_TIMING, STATE_RUNNING_PROFILE, STATE_FINISHED
    }

    private DriveTrain driveTrain;
    private TrajectoryExecutionState currState = TrajectoryExecutionState.STATE_STABILIZING_TIMING;
    private CircularBuffer dtBuffer;
    private double lastTime;
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
    private double gyroCompensator = 0;
    private double leftGyroIntegrator = 0;
    private double rightGyroIntegrator = 0;
    private Object lock = new Object();

    public static final Trajectory.Config DEFAULT_CONFIG = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_LOW, DEFAULT_DT, DEFAULT_MAX_VEL, DEFAULT_MAX_ACCEL, DEFAULT_MAX_JERK);

    public TrajectoryExecutor(Waypoint[] waypoints, Trajectory.Config config, double time, boolean rev, double gyroOffset) {
        driveTrain = Robot.runningRobot.driveTrain;
        reversed = rev;
        directionMultiplier = reversed ? -1 : 1;
        DriverStation.reportError("starting generation...", false);
        master = Pathfinder.generate(waypoints, config);
        DriverStation.reportError("done!", false);
        TankModifier mod = new TankModifier(master).modify(RobotConstants.WHEEL_BASE_WIDTH);
        if (reversed) {
            leftTraj = mod.getRightTrajectory();
            rightTraj = mod.getLeftTrajectory();
        } else {
            leftTraj = mod.getLeftTrajectory();
            rightTraj = mod.getRightTrajectory();
        }
        gyroCompensator = gyroOffset;
        currentPos = driveTrain.getDistance();
        dt = config.dt;

        leftFollow = new EncoderFollower(leftTraj);
        rightFollow = new EncoderFollower(rightTraj);
        leftFollow.configurePIDVA(KP, KI, KD, KV, KA);
        rightFollow.configurePIDVA(KP, KI, KD, KV, KA);
        leftFollow.configureEncoder(0, DriveTrainSide.ENCODER_CODES_PER_REV * 4, RobotConstants.WHEEL_DIAMETER);
        rightFollow.configureEncoder(0, DriveTrainSide.ENCODER_CODES_PER_REV * 4, RobotConstants.WHEEL_DIAMETER);
        timeout = time;
        runner = new Notifier(this);

        dtBuffer = new CircularBuffer(NUM_SAMPLES);
        fillBuffer(UNINITIALIZED_SENTINEL);
    }

    public TrajectoryExecutor(Waypoint[] waypoints, double time, boolean rev, double gyroOffset) {
        this(waypoints, DEFAULT_CONFIG, time, rev, gyroOffset);
    }

    public TrajectoryExecutor(Waypoint[] waypoints, boolean rev, double gyroOffset) {
        this(waypoints, DEFAULT_CONFIG, DEFAULT_TIMEOUT, rev, gyroOffset);
    }

    public void fillBuffer(double val) {
        for (int i = 0; i < NUM_SAMPLES; i++) {
            dtBuffer.addFirst(val);
        }
    }

    public double getDTRunningAverage() {
        double sum = 0;
        int numPoints = 0;
        for (int i = 0; i < NUM_SAMPLES; i++) {
            double val = dtBuffer.get(i);
            if (val != UNINITIALIZED_SENTINEL) {
                sum += val;
                numPoints++;
            }
        }
        return sum / numPoints;
    }

    public void start() {
        synchronized (lock) {
            runner.startPeriodic(dt);
            driveTrain.resetEnc();
            lastTime = Timer.getFPGATimestamp();
            endTime = lastTime + timeout;
            running = true;
            DriverStation.reportError("Starting Trajectory...", false);
        }
    }

    @Override
    public void run() {
        double time = Timer.getFPGATimestamp();
        switch (currState) {
            case STATE_STABILIZING_TIMING:
                dtBuffer.addFirst(time - lastTime);
                lastTime = time;
                if (Math.abs(getDTRunningAverage() - dt) < .00001) {
                    currState = TrajectoryExecutionState.STATE_RUNNING_PROFILE;
                }
                break;
            case STATE_RUNNING_PROFILE:
                currentPos = driveTrain.getDistance();
                currentHeading = driveTrain.getYaw();
                Segment seg = leftFollow.getSegment();

                double headDiff = Pathfinder.boundHalfDegrees(currentHeading + gyroCompensator - Pathfinder.r2d(seg.heading));
                double leftGyroPower = K_HEADING * headDiff;
                double rightGyroPower = -K_HEADING * headDiff;
                double leftTwist =
                        leftGyroPower / KV / DriveTrainSide.DIST_PER_REV * DriveTrainSide.ENCODER_CODES_PER_REV * dt;
                double rightTwist =
                        rightGyroPower / KV / DriveTrainSide.DIST_PER_REV * DriveTrainSide.ENCODER_CODES_PER_REV * dt;
                leftGyroIntegrator += leftTwist;
                rightGyroIntegrator += rightTwist;

                double left = directionMultiplier * (leftFollow
                        .calculate(directionMultiplier * ((int) currentPos.getX() - (int) leftGyroIntegrator))
                        + K_OFFSET * Math.signum(seg.velocity));
                double right = directionMultiplier * (rightFollow
                        .calculate(directionMultiplier * ((int) currentPos.getY() - (int) rightGyroIntegrator))
                        + K_OFFSET * Math.signum(seg.velocity));
                driveTrain.setPowerLeftRight(left, right);
                Robot.runningRobot.logger.storeValue(
                        new double[] { (currentPos.getX() + currentPos.getY()) / 2, driveTrain.getAvgSideVelocity(),
                                seg.position, seg.velocity, currentHeading, Pathfinder.r2d(seg.heading), time });
                if (leftFollow.isFinished() || rightFollow.isFinished() || time > endTime) {
                    currState = TrajectoryExecutionState.STATE_FINISHED;
                }
                break;
            case STATE_FINISHED:
                synchronized (lock) {
                    isFinished = true;
                    stop();
                    DriverStation.reportError("Trajectory complete", false);
                    driveTrain.stop();
                }
                break;
            default:
                break;
        }
    }

    public void stop() {
        synchronized (lock) {
            runner.stop();
            running = false;
            Robot.runningRobot.logger.flushToDisk();
        }
    }

    public boolean isRunning() {
        synchronized (lock) {
            return running;
        }
    }

    public boolean isFinished() {
        synchronized (lock) {
            return isFinished;
        }
    }
}

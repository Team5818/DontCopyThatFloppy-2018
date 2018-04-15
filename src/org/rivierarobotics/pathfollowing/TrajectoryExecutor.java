package org.rivierarobotics.pathfollowing;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.pathfollowing.WiggleModifier.WiggleConfig;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.subsystems.DriveTrainSide;
import org.rivierarobotics.util.Vector2d;

import edu.wpi.first.wpilibj.CircularBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Config;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class TrajectoryExecutor implements Runnable {

    public static final int NUM_SAMPLES = 20;
    public static final double UNINITIALIZED_SENTINEL = -254;
    public static final double DEFAULT_DT = .01;
    public static final double DEFAULT_MAX_VEL = 80;
    public static final double DEFAULT_MAX_ACCEL = 100;
    public static final double DEFAULT_MAX_JERK = 500;
    public static final double MAX_VEL_HIGH = 130;
    public static final double MAX_ACCEL_HIGH = 120;
    public static final double KP = 0.1;
    public static final double KI = 0.0;
    public static final double KD = 0.0;
    public static final double KV = 0.0086;
    public static final double KA = 0.0024;
    public static final double KP_HIGH = 0.1;
    public static final double KI_HIGH = 0.0;
    public static final double KD_HIGH = 0.0;
    public static final double KV_HIGH = 0.0045;
    public static final double KA_HIGH = 0.0025;
    public static final double K_OFFSET = 0.045;
    public static final double K_OFFSET_HIGH = 0.0717;
    public static final double K_HEADING_DEFAULT = 0.03;
    public static final double K_HEADING_HIGH = 0.015;

    public enum TrajectoryExecutionState {
        STATE_STABILIZING_TIMING, STATE_RUNNING_PROFILE, STATE_FINISHED, STATE_SENSOR_FAULT
    }

    private double kHeading;
    private double kOffset;
    private double kv;
    private DriveTrain driveTrain;
    private TrajectoryExecutionState currState = TrajectoryExecutionState.STATE_STABILIZING_TIMING;
    private CircularBuffer dtBuffer;
    private double lastTime;
    private boolean running = false;
    private boolean isFinished = false;
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
    private double sensorFaultTime;
    private DriveGear gear;
    private Object lock = new Object();

    public static final Trajectory.Config CONFIG_LOW = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_LOW, DEFAULT_DT, DEFAULT_MAX_VEL, DEFAULT_MAX_ACCEL, DEFAULT_MAX_JERK);
    
    public static final Trajectory.Config CONFIG_HIGH = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_LOW, DEFAULT_DT, MAX_VEL_HIGH, MAX_ACCEL_HIGH, DEFAULT_MAX_JERK);

    public TrajectoryExecutor(Waypoint[] waypoints, boolean rev,
            double gyroOffset, double kGyro, DriveGear g, double speed) {
        gear = g;
        driveTrain = Robot.runningRobot.driveTrain;
        reversed = rev;
        directionMultiplier = reversed ? -1 : 1;
        DriverStation.reportError("starting generation...", false);
        Config config;
        if(g == DriveGear.GEAR_HIGH) {
            config = CONFIG_HIGH;
            kv = KV_HIGH;
            kOffset = K_OFFSET_HIGH;
            kHeading = K_HEADING_HIGH;
        }
        else {
            config = CONFIG_LOW;
            kv = KV;
            kOffset = K_OFFSET;
            kHeading = K_HEADING_DEFAULT;
 
        }
        if(!Double.isNaN(kGyro)) {
            kHeading = kGyro;
        }
        if(!Double.isNaN(speed)) {
            config.max_velocity = speed;
        }
        master = Pathfinder.generate(waypoints, config);
        DriverStation.reportError("done!", false);
        TankModifier mod = new TankModifier(master).modify(RobotConstants.WHEEL_BASE_WIDTH);
        int lCPR, rCPR;
        if (reversed) {
            leftTraj = mod.getRightTrajectory();
            lCPR = DriveTrainSide.ENCODER_CODES_PER_REV * 4;
            rightTraj = mod.getLeftTrajectory();
            rCPR = 5000*4;
        } else {
            leftTraj = mod.getLeftTrajectory();
            lCPR = 5000 * 4;
            rightTraj = mod.getRightTrajectory();
            rCPR = DriveTrainSide.ENCODER_CODES_PER_REV * 4;
        }
        gyroCompensator = gyroOffset;
        currentPos = driveTrain.getDistance();
        dt = config.dt;

        leftFollow = new EncoderFollower(leftTraj);
        rightFollow = new EncoderFollower(rightTraj);
        if(g == DriveGear.GEAR_HIGH) {
            leftFollow.configurePIDVA(KP_HIGH, KI_HIGH, KD_HIGH, KV_HIGH, KA_HIGH);
            rightFollow.configurePIDVA(KP_HIGH, KI_HIGH, KD_HIGH, KV_HIGH, KA_HIGH);
        }
        else {
            leftFollow.configurePIDVA(KP, KI, KD, KV, KA);
            rightFollow.configurePIDVA(KP, KI, KD, KV, KA); 
        }
        leftFollow.configureEncoder(0, lCPR, RobotConstants.WHEEL_DIAMETER);
        rightFollow.configureEncoder(0, rCPR, RobotConstants.WHEEL_DIAMETER);
        runner = new Notifier(this);

        dtBuffer = new CircularBuffer(NUM_SAMPLES);
        fillBuffer(UNINITIALIZED_SENTINEL);
    }


    public TrajectoryExecutor(Waypoint[] waypoints, boolean rev, double gyroOffset, DriveGear g) {
        this(waypoints, rev, gyroOffset, Double.NaN, g, Double.NaN);
    }
    
    public TrajectoryExecutor(Waypoint[] waypoints, boolean rev, double gyroOffset) {
        this(waypoints, rev, gyroOffset, Double.NaN, DriveGear.GEAR_LOW, Double.NaN);
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
    
    public void reset() {
        leftGyroIntegrator = 0;
        rightGyroIntegrator = 0;
        fillBuffer(UNINITIALIZED_SENTINEL);
        leftFollow.reset();
        rightFollow.reset();
        leftFollow.configureEncoder(0, DriveTrainSide.ENCODER_CODES_PER_REV * 4, RobotConstants.WHEEL_DIAMETER);
        rightFollow.configureEncoder(0, DriveTrainSide.ENCODER_CODES_PER_REV * 4, RobotConstants.WHEEL_DIAMETER);
        currState = TrajectoryExecutionState.STATE_STABILIZING_TIMING;
        isFinished = false;
    }

    public void start() {
        synchronized (lock) {
            runner.startPeriodic(dt);
            driveTrain.resetEnc();
            driveTrain.shiftGear(gear);
            lastTime = Timer.getFPGATimestamp();
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
                Segment segL = leftFollow.getSegment();
                Segment segR = rightFollow.getSegment();
                double headDiff =
                        Pathfinder.boundHalfDegrees(currentHeading + gyroCompensator - Pathfinder.r2d(segL.heading));
                double leftGyroPower = kHeading * headDiff;
                double rightGyroPower = -kHeading * headDiff;
                double leftTwist =
                        leftGyroPower / kv / DriveTrainSide.DIST_PER_REV * DriveTrainSide.ENCODER_CODES_PER_REV * dt;
                double rightTwist =
                        rightGyroPower / kv / DriveTrainSide.DIST_PER_REV * DriveTrainSide.ENCODER_CODES_PER_REV * dt;
                leftGyroIntegrator += leftTwist;
                rightGyroIntegrator += rightTwist;

                double left = directionMultiplier * (leftFollow
                        .calculate(directionMultiplier * ((int) currentPos.getX() - (int) leftGyroIntegrator))
                        + kOffset * Math.signum(segL.velocity));// + leftGyroPower;
                double right = directionMultiplier * (rightFollow
                        .calculate(directionMultiplier * ((int) currentPos.getY() - (int) rightGyroIntegrator))
                        + kOffset * Math.signum(segR.velocity));// + rightGyroPower;
                driveTrain.setPowerLeftRight(left, right);
                Vector2d vel = driveTrain.getVelocityIPS();
                Robot.runningRobot.logger.storeValue(new double[] {
                        currentPos.getX() / (double)(DriveTrainSide.ENCODER_CODES_PER_REV) * DriveTrainSide.DIST_PER_REV / 4.0,
                        currentPos.getY() / (double)(DriveTrainSide.ENCODER_CODES_PER_REV) * DriveTrainSide.DIST_PER_REV / 4.0,
                        vel.getX(), vel.getY(), segL.position, segR.position, segL.velocity, segR.velocity,
                        leftGyroIntegrator, rightGyroIntegrator, currentHeading, Pathfinder.r2d(segL.heading), left,
                        right, time });
                if (leftFollow.isFinished() || rightFollow.isFinished()) {
                    currState = TrajectoryExecutionState.STATE_FINISHED;
                } 
                break;
            case STATE_SENSOR_FAULT:
                if(Timer.getFPGATimestamp() < sensorFaultTime + 2.5) {
                    driveTrain.setPowerLeftRight(directionMultiplier * kv * DEFAULT_MAX_VEL/3.0, directionMultiplier * KV * DEFAULT_MAX_VEL/3.0);
                }
                else {
                    driveTrain.stop();
                }
                break;
            case STATE_FINISHED:
                synchronized (lock) {
                    isFinished = true;
                    stop();
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
            DriverStation.reportError("Trajectory complete", false);
            driveTrain.stop();
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

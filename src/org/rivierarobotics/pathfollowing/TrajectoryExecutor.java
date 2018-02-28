package org.rivierarobotics.pathfollowing;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.mathUtil.Vector2d;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.subsystems.DriveTrainSide;

import edu.wpi.first.wpilibj.Notifier;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class TrajectoryExecutor implements Runnable{

    private DriveTrain driveTrain;
    private boolean running = false;
    private double timeout;
    private Trajectory leftTraj;
    private Trajectory rightTraj;
    private EncoderFollower leftFollow;
    private EncoderFollower rightFollow;
    private Trajectory master;
    private double dt;
    private Notifier runner;
    private Vector2d currentPos;
    private double currentHeading;

    public static final double DEFAULT_DT = .005;
    public static final double DEFAULT_MAX_VEL = 400;
    public static final double DEFAULT_MAX_ACCEL = 800;
    public static final double DEFAULT_MAX_JERK = Double.POSITIVE_INFINITY;
    private static final double DEFAULT_TIMEOUT = Double.POSITIVE_INFINITY;
    
    private static final double KP = 0.0;
    private static final double KI = 0.0;
    private static final double KD = 0.0;
    private static final double KV = 0.0;
    private static final double KA = 0.0;

    
    private static final Trajectory.Config DEFAULT_CONFIG = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_HIGH, DEFAULT_DT, DEFAULT_MAX_VEL, DEFAULT_MAX_ACCEL, DEFAULT_MAX_JERK);

    public TrajectoryExecutor(Waypoint[] waypoints, Trajectory.Config config, double time) {
        driveTrain = Robot.runningRobot.driveTrain;
        master = Pathfinder.generate(waypoints, config);
        TankModifier mod = new TankModifier(master).modify(RobotConstants.WHEEL_BASE_WIDTH);
        leftTraj = mod.getLeftTrajectory();
        rightTraj = mod.getRightTrajectory();
        
        currentPos = driveTrain.getDistance();
        
        leftFollow = new EncoderFollower(leftTraj);
        rightFollow = new EncoderFollower(rightTraj);
        leftFollow.configurePIDVA(KP, KI, KD, KV, KA);
        rightFollow.configurePIDVA(KP, KI, KD, KV, KA);
        leftFollow.configureEncoder((int)currentPos.getX(), DriveTrainSide.ENCODER_CODES_PER_REV, RobotConstants.WHEEL_DIAMETER);
        rightFollow.configureEncoder((int)currentPos.getY(), DriveTrainSide.ENCODER_CODES_PER_REV, RobotConstants.WHEEL_DIAMETER);
        timeout = time;
    }

    public TrajectoryExecutor(Waypoint[] waypoints, double time) {
        this(waypoints, DEFAULT_CONFIG, time);
    }
    

    public TrajectoryExecutor(Waypoint[] waypoints) {
        this(waypoints, DEFAULT_CONFIG, DEFAULT_TIMEOUT);
    }
    
    @Override
    public void run() {
        currentPos = driveTrain.getDistance();
        
        double left = leftFollow.calculate((int)currentPos.getX());
        double right = rightFollow.calculate((int)currentPos.getY());
        
        driveTrain.setPowerLeftRight(left, right);
    }
}

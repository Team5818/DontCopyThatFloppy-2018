package org.rivierarobotics.autos.leftscale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class DriveToScaleLeft extends SideDependentTrajectoryExecutor {

    public static final double X_OFFSET = RobotConstants.TOTAL_ROBOT_LENGTH / 2;
    public static final double Y_OFFSET = MathUtil.feet2inches(3.68) + RobotConstants.TOTAL_ROBOT_WIDTH / 2;
    public static final double RIGHT_PLACING_X = MathUtil.feet2inches(26.5);
    public static final double RIGHT_PLACING_Y = MathUtil.feet2inches(7.0);

    public static final Waypoint[] LEFT_PATH =
//            new Waypoint[] { 
//                    new Waypoint(0, 0, 0), 
//                    new Waypoint(MathUtil.feet2inches(15) - X_OFFSET, 0, 0),
//                    new Waypoint(MathUtil.feet2inches(25.25) - X_OFFSET, MathUtil.feet2inches(8.25) - Y_OFFSET, 0) };
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(27) - X_OFFSET, 0, 0) };

    public static final Waypoint[] RIGHT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(20.4) - X_OFFSET, 0, 0)};

    public DriveToScaleLeft() {
        requires(Robot.runningRobot.driveTrain);
        leftExecutor = new TrajectoryExecutor(LEFT_PATH, false, 0, DriveGear.GEAR_HIGH);
        rightExecutor = new TrajectoryExecutor(RIGHT_PATH, false, 0, DriveGear.GEAR_HIGH);
    }

    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
}
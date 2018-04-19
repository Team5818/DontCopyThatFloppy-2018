package org.rivierarobotics.autos.baselinescale;

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

public class DriveToScaleLeftBL extends SideDependentTrajectoryExecutor {

    public static final double X_OFFSET = RobotConstants.TOTAL_ROBOT_LENGTH / 2;
    public static final double Y_OFFSET = MathUtil.feet2inches(23.32); // width already included

    public static final Waypoint[] LEFT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(16.00) - X_OFFSET, 0, 0),
                    new Waypoint(MathUtil.feet2inches(25.0) - X_OFFSET, MathUtil.feet2inches(22) - Y_OFFSET,
                            Pathfinder.d2r(-50)) };

    public static final Waypoint[] RIGHT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(3) - X_OFFSET, 0, 0) };

    public DriveToScaleLeftBL() {
        requires(Robot.runningRobot.driveTrain);
        leftExecutor = new TrajectoryExecutor(LEFT_PATH, false, 0, DriveGear.GEAR_HIGH);
        rightExecutor = new TrajectoryExecutor(RIGHT_PATH, false, 0, DriveGear.GEAR_HIGH);
    }

    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
}

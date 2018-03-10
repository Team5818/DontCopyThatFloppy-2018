package org.rivierarobotics.autos;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class DriveToSwitchFromCenter extends Command {

    public static final Waypoint[] RIGHT_PATH =
            new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(80 - RobotConstants.TOTAL_ROBOT_LENGTH, 0, 0),
                    new Waypoint(100 - RobotConstants.TOTAL_ROBOT_LENGTH, 11, Pathfinder.d2r(-45)),
                    new Waypoint(120 - RobotConstants.TOTAL_ROBOT_LENGTH, 22.375, 0),
                    new Waypoint(140 - RobotConstants.TOTAL_ROBOT_LENGTH, 22.375, 0)

            };

    public static final Waypoint[] LEFT_PATH =
            new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(80 - RobotConstants.TOTAL_ROBOT_LENGTH, 0, 0),
                    new Waypoint(100 - RobotConstants.TOTAL_ROBOT_LENGTH, -11, Pathfinder.d2r(45)),
                    new Waypoint(120 - RobotConstants.TOTAL_ROBOT_LENGTH, -22.375, 0),
                    new Waypoint(140 - RobotConstants.TOTAL_ROBOT_LENGTH, -22.375, 0)

            };

    private TrajectoryExecutor leftEx;
    private TrajectoryExecutor rightEx;
    private TrajectoryExecutor selectedEx;
    private Side[] fieldData;

    public DriveToSwitchFromCenter() {
        requires(Robot.runningRobot.driveTrain);
        leftEx = new TrajectoryExecutor(LEFT_PATH, false);
        rightEx = new TrajectoryExecutor(RIGHT_PATH, false);
    }

    @Override
    protected void initialize() {
        fieldData = Robot.runningRobot.getSide();
        if (fieldData[0] == Side.LEFT) {
            selectedEx = leftEx;
        } else {
            selectedEx = rightEx;
        }
        selectedEx.start();
    }

    @Override
    protected boolean isFinished() {
        return selectedEx.isFinished();
    }
    
    @Override
    protected void interrupted() {
        selectedEx.stop();
    }
}

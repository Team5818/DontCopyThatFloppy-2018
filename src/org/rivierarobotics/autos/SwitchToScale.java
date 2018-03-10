package org.rivierarobotics.autos;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class SwitchToScale extends Command {

    public static final Waypoint[] RIGHT_TO_LEFT_PATH =
            new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(30 - RobotConstants.TOTAL_ROBOT_LENGTH, 0, 0),
                    new Waypoint(100 - RobotConstants.TOTAL_ROBOT_LENGTH, 11, Pathfinder.d2r(-45)),
                    new Waypoint(120 - RobotConstants.TOTAL_ROBOT_LENGTH, 22.375, 0),
                    new Waypoint(140 - RobotConstants.TOTAL_ROBOT_LENGTH, 22.375, 0)

            };

    public static final Waypoint[] LEFT_TO_RIGHT_PATH =
            new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(80 - RobotConstants.TOTAL_ROBOT_LENGTH, 0, 0),
                    new Waypoint(100 - RobotConstants.TOTAL_ROBOT_LENGTH, -11, Pathfinder.d2r(45)),
                    new Waypoint(120 - RobotConstants.TOTAL_ROBOT_LENGTH, -22.375, 0),
                    new Waypoint(140 - RobotConstants.TOTAL_ROBOT_LENGTH, -22.375, 0)

            };
    
    public static final Waypoint[] RIGHT_TO_RIGHT_PATH =
            new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(80 - RobotConstants.TOTAL_ROBOT_LENGTH, 0, 0),
                    new Waypoint(100 - RobotConstants.TOTAL_ROBOT_LENGTH, 11, Pathfinder.d2r(-45)),
                    new Waypoint(120 - RobotConstants.TOTAL_ROBOT_LENGTH, 22.375, 0),
                    new Waypoint(140 - RobotConstants.TOTAL_ROBOT_LENGTH, 22.375, 0)

            };

    public static final Waypoint[] LEFT_TO_LEFT_PATH =
            new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(80 - RobotConstants.TOTAL_ROBOT_LENGTH, 0, 0),
                    new Waypoint(100 - RobotConstants.TOTAL_ROBOT_LENGTH, -11, Pathfinder.d2r(45)),
                    new Waypoint(120 - RobotConstants.TOTAL_ROBOT_LENGTH, -22.375, 0),
                    new Waypoint(140 - RobotConstants.TOTAL_ROBOT_LENGTH, -22.375, 0)

            };


    private TrajectoryExecutor left2rightEx;
    private TrajectoryExecutor right2leftEx;
    private TrajectoryExecutor left2leftEx;
    private TrajectoryExecutor right2rightEx;
    private TrajectoryExecutor selectedEx;
    private Side[] fieldData;

    public SwitchToScale() {
        requires(Robot.runningRobot.driveTrain);
        left2leftEx = new TrajectoryExecutor(LEFT_TO_LEFT_PATH, false);
        right2rightEx = new TrajectoryExecutor(RIGHT_TO_RIGHT_PATH, false);
        left2rightEx = new TrajectoryExecutor(LEFT_TO_RIGHT_PATH, false);
        right2leftEx = new TrajectoryExecutor(RIGHT_TO_LEFT_PATH, false);
    }

    @Override
    protected void initialize() {
        fieldData = Robot.runningRobot.getSide();
        if (fieldData[0] == Side.LEFT && fieldData[1] == Side.LEFT) {
            selectedEx = left2leftEx;
        }
        if (fieldData[0] == Side.RIGHT && fieldData[1] == Side.RIGHT) {
            selectedEx = right2rightEx;
        }
        if (fieldData[0] == Side.LEFT && fieldData[1] == Side.RIGHT) {
            selectedEx = left2rightEx;
        }
        if (fieldData[0] == Side.RIGHT && fieldData[1] == Side.LEFT) {
            selectedEx = right2leftEx;
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
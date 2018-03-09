package autos;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class DriveToSwitchFromRight extends Command {

    public static final Waypoint[] RIGHT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(120 - RobotConstants.TOTAL_ROBOT_LENGTH, 0, 0),
                    new Waypoint(164 - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0,
                            56.5 - RobotConstants.TOTAL_ROBOT_LENGTH, Pathfinder.d2r(90))

            };

    public static final Waypoint[] LEFT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(200 - RobotConstants.TOTAL_ROBOT_LENGTH, 0, 0),
                    new Waypoint(220 - RobotConstants.TOTAL_ROBOT_WIDTH/ 2.0,
                            -56.5 + RobotConstants.TOTAL_ROBOT_LENGTH, Pathfinder.d2r(90)),
                    new Waypoint(220 - RobotConstants.TOTAL_ROBOT_WIDTH/ 2.0,
                            -240, Pathfinder.d2r(90)),
                    new Waypoint(200 - RobotConstants.TOTAL_ROBOT_WIDTH/2.0,
                            -300, Pathfinder.d2r(180)),
                    new Waypoint(160 - RobotConstants.TOTAL_ROBOT_WIDTH/2.0,
                            -300, Pathfinder.d2r(180)),
                    new Waypoint(140 - RobotConstants.TOTAL_ROBOT_WIDTH/2.0,
                            -240, Pathfinder.d2r(270))
            };

    private TrajectoryExecutor leftEx;
    private TrajectoryExecutor rightEx;
    private TrajectoryExecutor selectedEx;
    private Side[] fieldData;

    public DriveToSwitchFromRight() {
        requires(Robot.runningRobot.driveTrain);
        leftEx = new TrajectoryExecutor(LEFT_PATH, false);
        rightEx = new TrajectoryExecutor(RIGHT_PATH, false);
    }

    @Override
    protected void initialize() {
        fieldData = new Side[]{Side.RIGHT, Side.RIGHT, Side.RIGHT};//Robot.runningRobot.getSide();
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

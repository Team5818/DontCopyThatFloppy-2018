package org.rivierarobotics.autos;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class SwitchToScale extends Command {

    public static final double SWITCH_PLACING_X_RIGHT = MathUtil.feet2inches(14.0);
    public static final double SWITCH_PLACING_Y_RIGHT = MathUtil.feet2inches(5.68);

    public static final Waypoint[] RIGHT_TO_LEFT_PATH = null;//TODO

    public static final Waypoint[] LEFT_TO_RIGHT_PATH = null; //TODO

    public static final Waypoint[] RIGHT_TO_RIGHT_PATH = new Waypoint[] { new Waypoint(0, 0, Pathfinder.d2r(90)),
            new Waypoint(MathUtil.feet2inches(14.25) - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0 - SWITCH_PLACING_X_RIGHT,
                    MathUtil.feet2inches(4.25) - RobotConstants.TOTAL_ROBOT_WIDTH / 2.0 - SWITCH_PLACING_Y_RIGHT, 0),
            new Waypoint(MathUtil.feet2inches(19) - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0 - SWITCH_PLACING_X_RIGHT,
                    MathUtil.feet2inches(6) - RobotConstants.TOTAL_ROBOT_WIDTH / 2.0 - SWITCH_PLACING_Y_RIGHT,
                    Pathfinder.d2r(45)),
            new Waypoint(MathUtil.feet2inches(21) - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0 - SWITCH_PLACING_X_RIGHT,
                    MathUtil.feet2inches(7.25) - RobotConstants.TOTAL_ROBOT_WIDTH / 2.0 - SWITCH_PLACING_Y_RIGHT, 0), };

    public static final Waypoint[] LEFT_TO_LEFT_PATH = null;//TODO

    private TrajectoryExecutor left2rightEx;
    private TrajectoryExecutor right2leftEx;
    private TrajectoryExecutor left2leftEx;
    private TrajectoryExecutor right2rightEx;
    private TrajectoryExecutor selectedEx;
    private Side[] fieldData;

    public SwitchToScale() {
        requires(Robot.runningRobot.driveTrain);
        left2leftEx = null;//new TrajectoryExecutor(LEFT_TO_LEFT_PATH, false);
        right2rightEx = new TrajectoryExecutor(RIGHT_TO_RIGHT_PATH, true);
        left2rightEx = null;//new TrajectoryExecutor(LEFT_TO_RIGHT_PATH, false);
        right2leftEx = null;//new TrajectoryExecutor(RIGHT_TO_LEFT_PATH, false);
    }

    @Override
    protected void initialize() {
        fieldData = new Side[] { Side.RIGHT, Side.RIGHT, Side.RIGHT };// Robot.runningRobot.getSide();
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
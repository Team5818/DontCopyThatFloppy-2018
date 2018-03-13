package org.rivierarobotics.autos.switchthenscale;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class DriveToSwitchFromRight extends Command {

    public static final double WALL_TO_START_CORNER = 28.8;// inches

    public static final Waypoint[] RIGHT_PATH = new Waypoint[] { //one simple turn
            new Waypoint(0, 0, 0),
            new Waypoint(MathUtil.feet2inches(11) - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0, 0, 0),
            new Waypoint(MathUtil.feet2inches(14) - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0,
                    MathUtil.feet2inches(5.86) - RobotConstants.TOTAL_ROBOT_WIDTH / 2.0 - WALL_TO_START_CORNER,
                    Pathfinder.d2r(90)) };

    public static final Waypoint[] LEFT_PATH = new Waypoint[] { //drive in a crazy square around the whole field
            new Waypoint(0, 0, 0),
            new Waypoint(MathUtil.feet2inches(18) - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0, 0, 0),
            new Waypoint(MathUtil.feet2inches(20) - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0,
                    MathUtil.feet2inches(6) - RobotConstants.TOTAL_ROBOT_WIDTH / 2.0 - WALL_TO_START_CORNER,
                    Pathfinder.d2r(90)),
            new Waypoint(MathUtil.feet2inches(20) - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0,
                    MathUtil.inches2feet(21.5) - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0 - WALL_TO_START_CORNER,
                    Pathfinder.d2r(90)),
            new Waypoint(MathUtil.feet2inches(18) - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0,
                    MathUtil.feet2inches(23.5) - RobotConstants.TOTAL_ROBOT_WIDTH / 2.0 - WALL_TO_START_CORNER,
                    Pathfinder.d2r(180)),
            new Waypoint(MathUtil.feet2inches(16) - RobotConstants.TOTAL_ROBOT_LENGTH / 2.0,
                    MathUtil.feet2inches(23.50) - RobotConstants.TOTAL_ROBOT_WIDTH / 2.0 - WALL_TO_START_CORNER,
                    Pathfinder.d2r(180)),
            new Waypoint(MathUtil.feet2inches(14) - RobotConstants.TOTAL_ROBOT_WIDTH / 2.0,
                    MathUtil.feet2inches(21.32) - RobotConstants.TOTAL_ROBOT_WIDTH / 2.0 - WALL_TO_START_CORNER,
                    Pathfinder.d2r(270)) };

    private TrajectoryExecutor leftEx;
    private TrajectoryExecutor rightEx;
    private TrajectoryExecutor selectedEx;
    private Side[] fieldData;

    public DriveToSwitchFromRight() {
        requires(Robot.runningRobot.driveTrain);
        leftEx = new TrajectoryExecutor(LEFT_PATH, false, 0);
        rightEx = new TrajectoryExecutor(RIGHT_PATH, false, 0);
    }

    @Override
    protected void initialize() {
        fieldData = new Side[] { Side.RIGHT, Side.RIGHT, Side.RIGHT };// Robot.runningRobot.getSide();
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

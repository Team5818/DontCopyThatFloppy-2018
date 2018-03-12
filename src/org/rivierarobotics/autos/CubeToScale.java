package org.rivierarobotics.autos;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class CubeToScale extends Command {

    public static final Waypoint[] RIGHT_PATH = new Waypoint[] { 
            new Waypoint(0, 0, 0),
            new Waypoint(MathUtil.feet2inches(1), MathUtil.feet2inches(-.5), Pathfinder.d2r(-45)),
            new Waypoint(MathUtil.feet2inches(5.5), MathUtil.feet2inches(-1.5), 0)};

    public static final Waypoint[] LEFT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0),
                    new Waypoint(MathUtil.feet2inches(3), MathUtil.feet2inches(1), Pathfinder.d2r(45)),
                    new Waypoint(MathUtil.feet2inches(6), MathUtil.feet2inches(2), 0) };

    private TrajectoryExecutor leftEx;
    private TrajectoryExecutor rightEx;
    private TrajectoryExecutor selectedEx;
    private Side[] fieldData;

    public CubeToScale() {
        requires(Robot.runningRobot.driveTrain);
        leftEx = new TrajectoryExecutor(LEFT_PATH, true, -180);
        rightEx = new TrajectoryExecutor(RIGHT_PATH, true, -180);
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

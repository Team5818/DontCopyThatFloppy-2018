package org.rivierarobotics.autos.rightscale;

import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class ScaleToCube extends Command {

    public static final double OFFSET_X_RIGHT = MathUtil.feet2inches(25);
    public static final double OFFSET_Y_RIGHT = MathUtil.feet2inches(6.5);

    public static final Waypoint[] RIGHT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(2), 0, 0),
                    new Waypoint(MathUtil.feet2inches(4.5), MathUtil.feet2inches(-1.6), Pathfinder.d2r(-90)) };

    public static final Waypoint[] LEFT_PATH = null;// TODO

    private TrajectoryExecutor leftEx;
    private TrajectoryExecutor rightEx;
    private TrajectoryExecutor selectedEx;
    private Side[] fieldData;

    public ScaleToCube() {
        requires(Robot.runningRobot.driveTrain);
        leftEx = null;// new TrajectoryExecutor(LEFT_TO_LEFT_PATH, false);
        rightEx = new TrajectoryExecutor(RIGHT_PATH, true, 0);
    }

    @Override
    protected void initialize() {
        fieldData = new Side[] { Side.RIGHT, Side.RIGHT, Side.RIGHT };// Robot.runningRobot.getSide();
        if (fieldData[1] == Side.LEFT) {
            selectedEx = leftEx;
        }
        if (fieldData[1] == Side.RIGHT) {
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
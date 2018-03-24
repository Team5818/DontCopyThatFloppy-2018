package org.rivierarobotics.autos.rightscale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class ScaleToCube extends SideDependentTrajectoryExecutor {

    public static final double OFFSET_X_RIGHT = MathUtil.feet2inches(25);
    public static final double OFFSET_Y_RIGHT = MathUtil.feet2inches(6.5);

    public static final Waypoint[] RIGHT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(3), 0, 0)};

    public static final Waypoint[] LEFT_PATH = RIGHT_PATH;

    public ScaleToCube() {
        requires(Robot.runningRobot.driveTrain);
        leftExecutor = new TrajectoryExecutor(LEFT_PATH, true, 0);
        rightExecutor = new TrajectoryExecutor(RIGHT_PATH, true, 0);
    }

    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
}
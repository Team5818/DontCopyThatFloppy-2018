package org.rivierarobotics.autos.switchthenscale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class CubeToScaleSTS extends SideDependentTrajectoryExecutor{

    public static final Waypoint[] RIGHT_PATH = new Waypoint[] { 
            new Waypoint(0, 0, 0),
            new Waypoint(MathUtil.feet2inches(.5), 0,0),
            new Waypoint(MathUtil.feet2inches(4.75), MathUtil.feet2inches(-1), 0)};

    public static final Waypoint[] LEFT_PATH =
            new Waypoint[] { 
            new Waypoint(0, 0, 0),
            new Waypoint(MathUtil.feet2inches(.5), 0,0),
            new Waypoint(MathUtil.feet2inches(4.75), MathUtil.feet2inches(1), 0)};

    public CubeToScaleSTS() {
        requires(Robot.runningRobot.driveTrain);
        leftExecutor = new TrajectoryExecutor(LEFT_PATH, true, -180);
        rightExecutor = new TrajectoryExecutor(RIGHT_PATH, true, -180);
    }

    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
}

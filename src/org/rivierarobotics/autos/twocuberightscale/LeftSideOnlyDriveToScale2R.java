package org.rivierarobotics.autos.twocuberightscale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;

import jaci.pathfinder.Waypoint;

public class LeftSideOnlyDriveToScale2R extends SideDependentTrajectoryExecutor{
    
    public LeftSideOnlyDriveToScale2R() {
        requires(Robot.runningRobot.driveTrain);
        leftExecutor = new TrajectoryExecutor(new Waypoint[] {new Waypoint(0,0,0),new Waypoint(60,0,0)},false,0);
        rightExecutor = null;
    }

    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }

}
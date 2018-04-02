package org.rivierarobotics.autos.leftscale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;

import jaci.pathfinder.Waypoint;

public class RightSideOnlyDriveToScaleL extends SideDependentTrajectoryExecutor{
    
    public RightSideOnlyDriveToScaleL() {
        requires(Robot.runningRobot.driveTrain);
        rightExecutor = new TrajectoryExecutor(new Waypoint[] {new Waypoint(0,0,0),new Waypoint(68,0,0)},false,0);
        leftExecutor = null;
    }

    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }

}

package org.rivierarobotics.autos.rightscale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.util.MathUtil;

import jaci.pathfinder.Waypoint;

public class LeftSideOnlyDriveAcrossField extends SideDependentTrajectoryExecutor{
    
    public LeftSideOnlyDriveAcrossField() {
        requires(Robot.runningRobot.driveTrain);
        leftExecutor = new TrajectoryExecutor(new Waypoint[] {new Waypoint(0,0,0),new Waypoint(MathUtil.feet2inches(17),0,0)},false,-90, DriveGear.GEAR_HIGH);
        rightExecutor = null;
    }

    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
}

package org.rivierarobotics.autos.baselinescale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.util.MathUtil;

import jaci.pathfinder.Waypoint;

public class RightSideOnlyDriveAcrossFieldBL extends SideDependentTrajectoryExecutor{
    
    public RightSideOnlyDriveAcrossFieldBL() {
        requires(Robot.runningRobot.driveTrain);
        rightExecutor = new TrajectoryExecutor(new Waypoint[] {new Waypoint(0,0,0),new Waypoint(MathUtil.feet2inches(21),0,0)},false, 90, DriveGear.GEAR_HIGH);
        leftExecutor = null;
    }

    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
}

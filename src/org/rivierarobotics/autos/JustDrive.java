package org.rivierarobotics.autos;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.util.MathUtil;

import jaci.pathfinder.Waypoint;

public class JustDrive extends SideDependentTrajectoryExecutor{
    
    public JustDrive() {
        requires(Robot.runningRobot.driveTrain);
        leftExecutor = new TrajectoryExecutor(new Waypoint[] {new Waypoint(0,0,0),new Waypoint(MathUtil.feet2inches(10),0,0)},false, 0, DriveGear.GEAR_HIGH);
        rightExecutor = null;
    }

    @Override
    protected boolean isRightSide() {
        return false;
    }
}

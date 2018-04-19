package org.rivierarobotics.autos.twocuberightscale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.util.MathUtil;

import jaci.pathfinder.Waypoint;

public class BackUpLeft2R extends SideDependentTrajectoryExecutor{
    
    public BackUpLeft2R() {
        requires(Robot.runningRobot.driveTrain);
        rightExecutor = null;
        leftExecutor =  new TrajectoryExecutor(new Waypoint[] {new Waypoint(0,0,0),new Waypoint(36,0,0)},true, 0);
    }

    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
}

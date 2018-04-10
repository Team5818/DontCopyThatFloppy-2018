package org.rivierarobotics.autos;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.pathfollowing.WiggleModifier.WiggleConfig;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.util.MathUtil;

import jaci.pathfinder.Waypoint;

public class JustWiggle extends SideDependentTrajectoryExecutor {

    public JustWiggle() {
        requires(Robot.runningRobot.driveTrain);
        WiggleConfig wc = new WiggleConfig(5, TrajectoryExecutor.MAX_VEL_WIGGLE, Math.PI/12);
        leftExecutor = new TrajectoryExecutor(
                new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(MathUtil.feet2inches(10), 0, 0) }, false, 0.0, 0.01,
                DriveGear.GEAR_LOW, wc);
        rightExecutor = null;
    }

    @Override
    protected boolean isRightSide() {
        return false;
    }
}

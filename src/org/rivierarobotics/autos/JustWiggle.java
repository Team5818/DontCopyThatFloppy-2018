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
        WiggleConfig wc = new WiggleConfig(5, Math.PI / 12, TrajectoryExecutor.MAX_VEL_HIGH);
        leftExecutor = new TrajectoryExecutor(
                new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(MathUtil.feet2inches(20), 0, 0) },
                Double.POSITIVE_INFINITY, false, 0.0, 0.0, DriveGear.GEAR_HIGH, wc);
        rightExecutor = null;
    }

    @Override
    protected boolean isRightSide() {
        return false;
    }
}

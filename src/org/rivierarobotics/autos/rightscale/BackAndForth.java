package org.rivierarobotics.autos.rightscale;

import org.rivierarobotics.commands.ExecuteTrajectoryCommand;
import org.rivierarobotics.commands.MagicSpin;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.pathfollowing.WiggleModifier.WiggleConfig;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import jaci.pathfinder.Waypoint;

public class BackAndForth extends CommandGroup {

    public BackAndForth() {
        TrajectoryExecutor first = new TrajectoryExecutor(
                new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(MathUtil.feet2inches(3), 0, 0) }, false, 0.0,
                Double.NaN, DriveGear.GEAR_LOW, 10);
        TrajectoryExecutor forward = new TrajectoryExecutor(
                new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(5, 0, 0) }, false, 0.0,
                Double.NaN, DriveGear.GEAR_LOW, 10);
        TrajectoryExecutor backward = new TrajectoryExecutor(
                new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(5, 0, 0) }, true, 0.0,
                Double.NaN, DriveGear.GEAR_LOW, 10);
        this.addSequential(new ExecuteTrajectoryCommand(first));
        this.addSequential(new ExecuteTrajectoryCommand(forward));
        this.addSequential(new ExecuteTrajectoryCommand(backward));
    }

    @Override
    protected boolean isFinished() {
        return Robot.runningRobot.floppies.cubeInPlace();
    }

}

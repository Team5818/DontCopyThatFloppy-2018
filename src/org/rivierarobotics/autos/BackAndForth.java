package org.rivierarobotics.autos;

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
        TrajectoryExecutor forward = new TrajectoryExecutor(
                new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(18, 0, 0) }, false, -180,
                Double.NaN, DriveGear.GEAR_LOW, 10);
        TrajectoryExecutor backward = new TrajectoryExecutor(
                new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(18, 0, 0) }, true, -180,
                Double.NaN, DriveGear.GEAR_LOW, 10);
        for(int i = 0; i < 5; i++) {
            this.addSequential(new ExecuteTrajectoryCommand(forward));
            this.addSequential(new ExecuteTrajectoryCommand(backward));
        }
    }

    @Override
    protected boolean isFinished() {
        return Robot.runningRobot.floppies.cubeInPlace();
    }

}

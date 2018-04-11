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

public class WiggleWiggleWiggle extends CommandGroup {

    public WiggleWiggleWiggle() {
        
        WiggleConfig wc = new WiggleConfig(2, TrajectoryExecutor.MAX_VEL_WIGGLE, 0);
        TrajectoryExecutor exFirst = new TrajectoryExecutor(
                new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(MathUtil.feet2inches(3), 0, 0)}, false, 0.0);
//        TrajectoryExecutor exForward = new TrajectoryExecutor(
//                new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(5, 0, 0) }, false, 0.0,
//                0.015, DriveGear.GEAR_LOW, wc);
//        TrajectoryExecutor exBackward = new TrajectoryExecutor(
//                new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(5, 0, 0) }, true, 0.0,
//                0.015, DriveGear.GEAR_LOW, wc);
        this.addSequential(new ExecuteTrajectoryCommand(exFirst));
//        for (int i = 0; i < 10; i++) {
//            this.addSequential(new ExecuteTrajectoryCommand(exBackward));
//            this.addSequential(new ExecuteTrajectoryCommand(exForward));
//
//        }
    }

    @Override
    protected boolean isFinished() {
        return Robot.runningRobot.floppies.cubeInPlace();
    }

}

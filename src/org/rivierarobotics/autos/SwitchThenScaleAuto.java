package org.rivierarobotics.autos;

import org.rivierarobotics.commands.CollectGrabRaise;
import org.rivierarobotics.commands.ExecuteTrajectoryCommand;
import org.rivierarobotics.commands.MagicSpin;
import org.rivierarobotics.commands.SetArmAngleGainScheduled;
import org.rivierarobotics.commands.SetClampOpen;
import org.rivierarobotics.constants.RobotDependentConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;
import jaci.pathfinder.Waypoint;

public class SwitchThenScaleAuto extends CommandGroup {

    public SwitchThenScaleAuto() {
        CommandGroup driveToSwitch = new CommandGroup();
        driveToSwitch.addParallel(new DriveToSwitchFromRight());
        driveToSwitch
                .addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
        this.addSequential(driveToSwitch);
        this.addSequential(new SetClampOpen(true));
        this.addSequential(new SwitchToScale());
        this.addSequential(new MagicSpin(-180));

        Waypoint[] driveToCube = new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(15, 0, 0), };

        CommandGroup collectGroup = new CommandGroup();
        collectGroup.addParallel(new ExecuteTrajectoryCommand(driveToCube, false, -180));
        collectGroup.addParallel(new CollectGrabRaise(false));

        this.addSequential(collectGroup);

        CommandGroup placeGroup = new CommandGroup();
        placeGroup.addParallel(new CubeToScale());
        CommandGroup raiseGroup = new CommandGroup();
        raiseGroup.addSequential(new TimedCommand(.5));
        raiseGroup.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionBack()));
        placeGroup.addParallel(raiseGroup);
        this.addSequential(placeGroup);
        this.addSequential(new SetClampOpen(true));

    }

}

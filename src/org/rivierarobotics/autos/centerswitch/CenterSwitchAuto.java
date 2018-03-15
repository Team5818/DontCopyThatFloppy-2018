package org.rivierarobotics.autos.centerswitch;

import org.rivierarobotics.commands.SetArmAngleGainScheduled;
import org.rivierarobotics.commands.SetClampOpen;
import org.rivierarobotics.constants.RobotDependentConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterSwitchAuto extends CommandGroup{

    public CenterSwitchAuto() {
        CommandGroup driveAndRaise = new CommandGroup();
        driveAndRaise.addParallel(new DriveToSwitchFromCenter());
        driveAndRaise.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
        this.addSequential(driveAndRaise);
        this.addSequential(new SetClampOpen(true));
    }
}

package org.rivierarobotics.autos.centerswitch;

import org.rivierarobotics.commands.SetArmAngleGainScheduled;
import org.rivierarobotics.commands.SetClampOpen;
import org.rivierarobotics.commands.ShiftGear;
import org.rivierarobotics.constants.RobotDependentConstants;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class CenterSwitchAuto extends CommandGroup{

    public CenterSwitchAuto() {
        CommandGroup driveAndRaise = new CommandGroup();
        driveAndRaise.addParallel(new DriveToSwitchFromCenter());
        driveAndRaise.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
        this.addSequential(driveAndRaise);
        this.addSequential(new SetClampOpen(true));
        this.addSequential(new TimedCommand(1.0));
        this.addSequential(new DriveDownfieldFromSwitch());
    }
}

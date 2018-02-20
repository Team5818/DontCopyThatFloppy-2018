package org.rivierarobotics.commands;

import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectGrabRaise extends CommandGroup{
        
    public CollectGrabRaise() {
        CommandGroup lowerGroup = new CommandGroup();
        lowerGroup.addParallel(new SetArmAngleGainScheduled(Arm.ARM_POSITION_COLLECT_STANDBY));
        lowerGroup.addParallel(new CollectWithSwitch(.8));
        this.addSequential(new SetClampOpen(true));
        this.addSequential(lowerGroup);
        this.addSequential(new SetClampOpen(false));
        this.addSequential(new SetArmAngleGainScheduled(Arm.ARM_POSITION_MID_SWITCH));
    }
    
}

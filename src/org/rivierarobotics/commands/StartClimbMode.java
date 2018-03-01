package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartClimbMode extends CommandGroup{
    
    public StartClimbMode(Joystick js) {
        this.addSequential(new StartWinching());
        this.addSequential(new ArmControlClimbMode(js));
    }
    
}

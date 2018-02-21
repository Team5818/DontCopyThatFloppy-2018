package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartClimbMode extends CommandGroup{
    
    public StartClimbMode(Joystick js) {
        this.addSequential(new StartWinching());
        //this.addSequential(new ArmControlCommand(js));
        //setInterruptible(false);
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
}

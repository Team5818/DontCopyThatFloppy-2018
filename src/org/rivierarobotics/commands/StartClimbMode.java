package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartClimbMode extends CommandGroup{
    public StartClimbMode() {
        this.addSequential(new StartWinching());
        this.addSequential(new ArmControlCommand(Robot.runningRobot.driver.JS_ARM));
        
        setInterruptible(false);
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }
}

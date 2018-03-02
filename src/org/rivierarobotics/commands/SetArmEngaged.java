package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class SetArmEngaged extends Command{
    Arm arm;
    boolean locked;
    
    public SetArmEngaged(boolean l) {
        arm = Robot.runningRobot.arm;
        locked = l;
    }
    
    @Override
    protected void initialize() {
        arm.setArmEngaged(locked);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
    
    
    
}

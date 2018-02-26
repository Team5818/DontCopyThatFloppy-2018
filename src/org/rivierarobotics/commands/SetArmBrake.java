package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class SetArmBrake extends Command{
    Arm arm;
    boolean locked;
    
    public SetArmBrake(boolean l) {
        arm = Robot.runningRobot.arm;
        locked = l;
    }
    
    @Override
    protected void initialize() {
        arm.setBrakeEngaged(locked);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
    
    
    
}

package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class SetArmPTO extends Command{
    Arm arm;
    boolean locked;
    
    public SetArmPTO(boolean l) {
        arm = Robot.runningRobot.arm;
        locked = l;
    }
    
    @Override
    protected void initialize() {
        arm.setPTOEngaged(locked);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
    
    
    
}

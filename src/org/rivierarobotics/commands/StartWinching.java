package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class StartWinching extends Command{

    private Arm arm;
    
    public StartWinching() {
        arm = Robot.runningRobot.arm;
        requires(arm);
    }
    
    @Override
    protected void initialize() {
        arm.setArmEngaged(false);
        arm.setPTOEngaged(true);
    }
    
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }
    
}

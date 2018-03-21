package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class StartWinching extends Command{

    private Arm arm;
    
    public StartWinching() {
        arm = Robot.runningRobot.arm;
    }
    
    @Override
    protected void initialize() {
        arm.setArmEngagedAndPTODisengaged(true);
        arm.setBrakeEngaged(false);
    }
    
    @Override
    protected boolean isFinished() {
        return true;
    }
    
}

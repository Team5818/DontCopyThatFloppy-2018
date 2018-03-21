package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class SetArmEngagedAndPTODisengaged extends Command{
    Arm arm;
    boolean locked;
    
    public SetArmEngagedAndPTODisengaged(boolean l) {
        arm = Robot.runningRobot.arm;
        locked = l;
    }
    
    @Override
    protected void initialize() {
        arm.setArmEngagedAndPTODisengaged(locked);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
    
    
    
}

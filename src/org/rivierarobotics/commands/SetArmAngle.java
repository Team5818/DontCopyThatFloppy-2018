package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

public class SetArmAngle extends Command{
    Arm arm = Robot.runningRobot.arm;
    double target;
    
    public SetArmAngle(double ang) {
        target = ang;
        requires(arm);
        setTimeout(3);
    }
    
    @Override
    protected void initialize() {
        arm.setAngle(target);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}

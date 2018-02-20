package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class SetArmPower extends Command{
    private Arm arm = Robot.runningRobot.arm;
    private double power;
    
    public SetArmPower(double pow) {
        power = pow;
        requires(arm);
    }
    
    @Override
    protected void initialize() {
        arm.setPower(power);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
    
}

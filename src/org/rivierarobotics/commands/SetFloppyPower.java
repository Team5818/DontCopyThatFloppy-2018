package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;
import org.rivierarobotics.subsystems.Floppies;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class SetFloppyPower extends Command{
    private Floppies flop = Robot.runningRobot.floppies;
    private double power;
    
    public SetFloppyPower(double pow) {
        power = pow;
        requires(flop);
    }
    
    @Override
    protected void initialize() {
        flop.setPower(power, power);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
    
}

package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;
import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class SetDrivePower extends Command{
    private DriveTrain drive = Robot.runningRobot.driveTrain;
    private double power;
    
    public SetDrivePower(double pow) {
        power = pow;
        requires(drive);
    }
    
    @Override
    protected void initialize() {
        drive.setPowerLeftRight(power, power);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
    
}

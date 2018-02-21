package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftGear extends Command{
    private DriveTrain dt;
    private DriveTrain.DriveGear gear;
    
    public ShiftGear(DriveTrain.DriveGear g) {
        dt = Robot.runningRobot.driveTrain;
        gear = g;
    }
    
    @Override 
    protected void initialize() {
        dt.shiftGear(gear);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}

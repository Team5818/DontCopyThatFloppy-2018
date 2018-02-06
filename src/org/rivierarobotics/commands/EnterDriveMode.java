package org.rivierarobotics.commands;

import org.rivierarobotics.driverinterface.Driver;
import org.rivierarobotics.mathUtil.DriveCalculator;
import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EnterDriveMode extends Command{
    
    private DriveCalculator calc;
    private Driver driver;
    
    public EnterDriveMode(DriveCalculator dc){
        calc = dc;
        driver = Robot.runningRobot.driver;
    }
    
    @Override
    protected void initialize() {
        driver.DRIVE_CALC = calc;
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
    
}

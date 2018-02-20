package org.rivierarobotics.subsystems;

import org.rivierarobotics.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Clamp extends Subsystem {

    private Solenoid leftPiston;
    private Solenoid rightPiston;
    private boolean isOpen;

    public Clamp() {
        leftPiston = new Solenoid(RobotMap.LEFT_CLAMP_PISTON_PORT);
        rightPiston = new Solenoid(RobotMap.RIGHT_CLAMP_PISTON_PORT);
        isOpen = false;
    }

    public void setOpen(boolean open) {
        DriverStation.reportError("yo", false);
        isOpen = open;
        leftPiston.set(!open);
        rightPiston.set(!open);
    }
    
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }

}
package org.rivierarobotics.subsystems;

import org.rivierarobotics.constants.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Clamp extends Subsystem {

    private Solenoid leftPiston;
    private Solenoid rightPiston;
    private boolean isOpen;
    private int actuations;

    public Clamp() {
        leftPiston = new Solenoid(RobotMap.LEFT_CLAMP_PISTON_PORT);
        rightPiston = new Solenoid(RobotMap.RIGHT_CLAMP_PISTON_PORT);
        isOpen = false;
        actuations = 0;
    }

    public void setOpen(boolean open) {
        isOpen = open;
        leftPiston.set(open);
        rightPiston.set(open);
    }
    
    public boolean isOpen() {
        return isOpen;
    }
    
    public int getActuations() {
    	return actuations;
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }

}
package org.rivierarobotics.subsystems;

import org.rivierarobotics.constants.RobotDependentConstants;
import org.rivierarobotics.constants.RobotMap;
import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Clamp extends Subsystem {

    private Solenoid leftPiston;
    private Solenoid rightPiston;
    private Solenoid puncher;
    private boolean isOpen;

    public Clamp() {
        leftPiston = new Solenoid(RobotMap.LEFT_CLAMP_PISTON_PORT);
        rightPiston = new Solenoid(RobotMap.RIGHT_CLAMP_PISTON_PORT);
        puncher = new Solenoid(RobotMap.PUNCHER_SOLENOID);
        isOpen = false;
    }

    public void setOpen(boolean open) {
        isOpen = open;
        leftPiston.set(open);
        rightPiston.set(open);
    }

    public void setPuncher(boolean extended) {
        if (Robot.runningRobot.arm.getPosition() < RobotDependentConstants.Constant.getPuncherMinHeight() || 
        		Robot.runningRobot.arm.getPosition() > RobotDependentConstants.Constant.getPuncherMaxHeight()) {
            extended = false;
        }
        puncher.set(extended);
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }

}
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
	
	public Clamp() {
		leftPiston = new Solenoid(RobotMap.LEFT_CLAMP_PISTON_PORT);
		rightPiston = new Solenoid(RobotMap.RIGHT_CLAMP_PISTON_PORT);
	}

	public void setOpen(boolean open) {
		if (open) {
            leftPiston.set(true);
			rightPiston.set(true);
		} else {
            leftPiston.set(false);
            rightPiston.set(false);
		}
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}

}
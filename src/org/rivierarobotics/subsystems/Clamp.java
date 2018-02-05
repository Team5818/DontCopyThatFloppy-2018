package org.rivierarobotics.subsystems;

import org.rivierarobotics.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Clamp extends Subsystem {

	private Solenoid topPiston;
	private DoubleSolenoid bottomPiston;
	
	public Clamp() {
		bottomPiston = new DoubleSolenoid(0, 1);
		topPiston = new Solenoid(2);
	}

	public void setOpen(boolean open) {
		if (open) {
		    bottomPiston.set(Value.kForward);
			topPiston.set(true);
		} else {
			bottomPiston.set(Value.kReverse);
			topPiston.set(false);
		}
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
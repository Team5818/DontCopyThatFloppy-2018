package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Clamp;

import edu.wpi.first.wpilibj.command.Command;

public class SetClampOpen extends Command {
	
	private boolean open;
	Clamp clamp;
	
	public SetClampOpen(boolean o) {
		clamp = Robot.runningRobot.clamp;
		open = o;
	}

	@Override
	protected void initialize() {
		clamp.setOpen(open);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}

package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Clamp;

import edu.wpi.first.wpilibj.command.Command;

public class SetPuncher extends Command {

	private Clamp clamp;
	private boolean extended;
	
	public SetPuncher(boolean ext) {
		extended = ext;
		clamp = Robot.runningRobot.clamp;
	}

	public void execute() {
		clamp.setPuncher(extended);
	}
	@Override
	protected boolean isFinished() {
		return true;
	}

}

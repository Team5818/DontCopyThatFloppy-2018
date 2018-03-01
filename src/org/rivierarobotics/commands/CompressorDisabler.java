package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CompressorDisabler extends Command {

	private static double minVoltage;

	public CompressorDisabler(double voltage) {
		minVoltage = voltage;
	}
	
	public CompressorDisabler() {
		this(11.0);
	}

	protected void execute() {
		if (Robot.runningRobot.pdp.getVoltage() < minVoltage) {
			Robot.runningRobot.compressor.stop();
		} else {
			Robot.runningRobot.compressor.start();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}

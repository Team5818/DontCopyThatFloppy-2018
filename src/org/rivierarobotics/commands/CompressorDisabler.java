package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CompressorDisabler extends Command {

	private static double minVoltage;
	private static double actuations;

	public CompressorDisabler(double voltage) {
		minVoltage = voltage;
	}
	
	public CompressorDisabler() {
		this(10.0);
	}

	protected void execute() {
		if (determineCompressorOn())
			Robot.runningRobot.compressor.start();
		else
			Robot.runningRobot.compressor.stop();
	}

	private boolean determineCompressorOn() {
		if (Robot.runningRobot.clamp.getActuations() > actuations)
			return true;
		if (Robot.runningRobot.driver.forceCompressorOn.get())
			return true;
		if (Robot.runningRobot.pdp.getVoltage() >  minVoltage)
			return true;
		return false;
	}
	@Override
	protected boolean isFinished() {
		return false;
	}
}

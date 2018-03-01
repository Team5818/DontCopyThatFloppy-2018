package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CompressorDisabler extends Command {

	private static double minVoltage;
	private static double maxActuations;

	public CompressorDisabler(double voltage, int actuations) {
		minVoltage = voltage;
		maxActuations = actuations;
	}
	
	public CompressorDisabler() {
		this(10.0, 15);
	}

	protected void execute() {
		if (determineCompressorOn())
			Robot.runningRobot.compressor.start();
		else
			Robot.runningRobot.compressor.stop();
	}

	private boolean determineCompressorOn() {
		if (Robot.runningRobot.clamp.getActuations() > maxActuations)
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

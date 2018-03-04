package org.rivierarobotics.commands;

import org.rivierarobotics.commands.LEDControlCommand.LightState;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class LEDCycleCommand extends CommandGroup {
	
	private double delay;
	public LEDCycleCommand(double del) {
		DriverStation.reportError("i'm running", false);
		delay = del;
		this.addSequential(new LEDControlCommand(LightState.BLINKING_BLUE));
		this.addSequential(new WaitCommand(delay));
		this.addSequential(new LEDControlCommand(LightState.BLINKING_GREEN));
		this.addSequential(new WaitCommand(delay));
		this.addSequential(new LEDControlCommand(LightState.BLINKING_RED));
		this.addSequential(new WaitCommand(delay));
		this.addSequential(new LEDControlCommand(LightState.BLINKING_YELLOW));
		this.addSequential(new WaitCommand(delay));
		this.addSequential(new LEDControlCommand(LightState.CRAWLING_BLUE));
		this.addSequential(new WaitCommand(delay));
		this.addSequential(new LEDControlCommand(LightState.CRAWLING_GREEN));
		this.addSequential(new WaitCommand(delay));
		this.addSequential(new LEDControlCommand(LightState.CRAWLING_RED));
		this.addSequential(new WaitCommand(delay));
		this.addSequential(new LEDControlCommand(LightState.CRAWLING_YELLOW));
		this.addSequential(new WaitCommand(delay));
		this.addSequential(new LEDControlCommand(LightState.SOLID_BLUE));
		this.addSequential(new WaitCommand(delay));
		this.addSequential(new LEDControlCommand(LightState.SOLID_GREEN));
		this.addSequential(new WaitCommand(delay));
		this.addSequential(new LEDControlCommand(LightState.SOLID_RED));
		this.addSequential(new WaitCommand(delay));
		this.addSequential(new LEDControlCommand(LightState.SOLID_YELLOW));
		this.addSequential(new WaitCommand(delay));
	}
}

package org.rivierarobotics.commands;

import org.rivierarobotics.commands.LEDControlCommand.LightState;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class LEDCycleCommand extends Command {
	
	private Timer timer;
	private int stateCount = 0;
	private int prevCount = 0;
	private double pastTime;
	private double waitTime;
	private LEDControlCommand ledCommand;
	
	public LEDCycleCommand(double delay) {
		timer = new Timer();
		pastTime = timer.get();
		waitTime = delay;
	}
	
	@Override
    public void execute() {
		if(stateCount == 12) {
			stateCount = 0;
		}
		
		switch(stateCount) {
		case 0:
			ledCommand = new LEDControlCommand(LightState.BLINKING_BLUE);
			break;
		case 1:
			ledCommand = new LEDControlCommand(LightState.BLINKING_GREEN);
			break;
		case 2:
			ledCommand = new LEDControlCommand(LightState.BLINKING_RED);
			break;
		case 3:
			ledCommand = new LEDControlCommand(LightState.BLINKING_YELLOW);
			break;
		case 4:
			ledCommand = new LEDControlCommand(LightState.CRAWLING_BLUE);
			break;
		case 5:
			ledCommand = new LEDControlCommand(LightState.CRAWLING_GREEN);
			break;
		case 6:
			ledCommand = new LEDControlCommand(LightState.CRAWLING_RED);
			break;
		case 7:
			ledCommand = new LEDControlCommand(LightState.CRAWLING_YELLOW);
			break;
		case 8:
			ledCommand = new LEDControlCommand(LightState.SOLID_BLUE);
			break;
		case 9:
			ledCommand = new LEDControlCommand(LightState.SOLID_GREEN);
			break;
		case 10:
			ledCommand = new LEDControlCommand(LightState.SOLID_RED);
			break;
		case 11:
			ledCommand = new LEDControlCommand(LightState.SOLID_YELLOW);
			break;
		}
		if(stateCount != prevCount && checkTimerPastSeconds(waitTime)) {
			ledCommand.execute();
			stateCount++;
		}
	}
	
	private boolean checkTimerPastSeconds(double timeInSecs) {
		return timer.get() - pastTime >= timeInSecs;
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}

package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.RSSerialPort;

import edu.wpi.first.wpilibj.command.Command;

public class LEDControlCommand extends Command {
	
	private RSSerialPort port;
	private byte stateByte;
	
	public enum LightState {
        OFF, SOLID_YELLOW, SOLID_GREEN, SOLID_RED, SOLID_BLUE, BLINKING_YELLOW, BLINKING_GREEN, BLINKING_RED, BLINKING_BLUE, CRAWLING_YELLOW, CRAWLING_GREEN, CRAWLING_RED, CRAWLING_BLUE, RAINBOW;
    }
	
	public LEDControlCommand(LightState state) {
		port = Robot.runningRobot.serialPort;
		requires(port);
		
		switch(state) {
		case OFF:
			stateByte = 'a';
			break;
		case SOLID_YELLOW:
			stateByte = 'b';
			break;
		case SOLID_GREEN:
			stateByte = 'c';
			break;
		case SOLID_RED:
			stateByte = 'd';
			break;
		case SOLID_BLUE:
			stateByte = 'e';
			break;
		case BLINKING_YELLOW:
			stateByte = 'f';
			break;
		case BLINKING_GREEN:
			stateByte = 'g';
			break;
		case BLINKING_RED:
			stateByte = 'h';
			break;
		case BLINKING_BLUE:
			stateByte = 'i';
			break;
		case CRAWLING_YELLOW:
			stateByte = 'j';
			break;
		case CRAWLING_GREEN:
			stateByte = 'k';
			break;
		case CRAWLING_RED:
			stateByte = 'l';
			break;
		case CRAWLING_BLUE:
			stateByte = 'm';
			break;
		case RAINBOW:
			stateByte = 'n';
			break;
		default: 
			stateByte = 'a';
			break;
		}
	}
	
	@Override
    public void execute() {
        port.writePatternToSerial(stateByte);
    }
	@Override
	protected boolean isFinished() {
		return false;
	}

}

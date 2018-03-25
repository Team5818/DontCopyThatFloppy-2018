package org.rivierarobotics.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class RSSerialPort extends Subsystem {

	private SerialPort port;
	
	public RSSerialPort() {
		port = new SerialPort(9600, SerialPort.Port.kOnboard);
	}
	
	public void writePatternToSerial(byte inputByte) {
		byte buffer[] = new byte[1];
		buffer[0] = inputByte;
		port.write(buffer, 1);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
}

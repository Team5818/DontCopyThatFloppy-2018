package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Floppies;

import edu.wpi.first.wpilibj.command.Command;

public class CollectWithSwitch extends Command{
	
	private Floppies flop;
	private double power;
	
	public CollectWithSwitch(double pow, double time)
	{
		flop = Robot.runningRobot.floppies;
		power = pow;
		setTimeout(time);
	}
	
	@Override
	public void initialize()
	{  
	    if(!Double.isNaN(power)){
	        flop.setPower(power, power);
	    }
	}
	
	
	@Override
	public boolean isFinished()
	{
		return flop.cubeInPlace() || isTimedOut();
	}
	
}

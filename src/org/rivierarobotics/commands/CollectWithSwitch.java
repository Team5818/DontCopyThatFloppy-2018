package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Floppies;

import edu.wpi.first.wpilibj.command.Command;

public class CollectWithSwitch extends Command{
	
	private Floppies flop;
	private double power;
	
	public CollectWithSwitch(double pow)
	{
		flop = Robot.runningRobot.floppies;
		power = pow;
	}
	
	@Override
	public void initialize()
	{
		flop.setPower(power, power);
	}
	
	
	@Override
	public boolean isFinished()
	{
		return flop.cubeInPlace();
	}
	
	@Override
	protected void end() 
	{
		flop.setPosition(Floppies.LEFT_ZERO_POS, Floppies.RIGHT_ZERO_POS);
	}

}

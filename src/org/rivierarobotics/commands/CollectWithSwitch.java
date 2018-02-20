package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Floppies;

import edu.wpi.first.wpilibj.command.Command;

public class CollectWithSwitch extends Command{
	
	private Floppies flop;
	private double leftPow;
	private double rightPow;
	
	public CollectWithSwitch()
	{
		flop = Robot.runningRobot.floppies;
	}
	
	public void getLeftPower(double left)
	{
		leftPow = left;
	}
	
	public void getRightPower(double right)
	{
		rightPow = right;
	}
	
	@Override
	public void initialize()
	{
		flop.setPower(leftPow, rightPow);
	}
	
	@Override
	public void execute()
	{
		if(flop.cubeInPlace())
		{
			flop.setPower(0,0);
		}
	}
	
	@Override
	public boolean isFinished()
	{
		return false;
	}

}

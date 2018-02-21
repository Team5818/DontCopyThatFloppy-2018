package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Floppies;

import edu.wpi.first.wpilibj.command.Command;

public class SetCollectorPower extends Command{
    
    private Floppies flop;
    private double power;
    
    public SetCollectorPower(double pow, double time)
    {
        flop = Robot.runningRobot.floppies;
        power = pow;
        setTimeout(time);
    }
    
    @Override
    public void initialize()
    {
        flop.setPower(power, power);
    }
    
    
    @Override
    public boolean isFinished()
    {
        return isTimedOut();
    }
    
    @Override
    protected void end() {
        flop.setPower(0.0, 0.0);
    }
    
}

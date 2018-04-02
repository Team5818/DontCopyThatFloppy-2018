package org.rivierarobotics.autos.rightscale;

import org.rivierarobotics.commands.MagicSpin;
import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class WiggleWiggleWiggle extends CommandGroup{
    public WiggleWiggleWiggle() {
        this.addSequential(new MagicSpin(-190, .5));
        this.addSequential(new MagicSpin(-170, .5));
        this.addSequential(new MagicSpin(-180, .5));
    }
    
    @Override
    protected boolean isFinished() {
        return Robot.runningRobot.floppies.cubeInPlace();
    }
    
}

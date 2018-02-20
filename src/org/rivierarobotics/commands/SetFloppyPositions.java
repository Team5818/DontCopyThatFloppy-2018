package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;
import org.rivierarobotics.subsystems.Floppies;

import edu.wpi.first.wpilibj.command.Command;

public class SetFloppyPositions extends Command{
    Floppies floppies;
    int targetL;
    int targetR;
    
    public SetFloppyPositions(int left, int right) {
        floppies = Robot.runningRobot.floppies;
        targetL = left;
        targetR = right;
        requires(floppies);
        setTimeout(1);
    }
    
    @Override
    protected void initialize() {
        floppies.setPosition(targetL, targetR);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}

package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;
import org.rivierarobotics.subsystems.Rollers;

import edu.wpi.first.wpilibj.command.Command;

public class SetRollerPositions extends Command{
    Rollers rollers;
    int targetL;
    int targetR;
    
    public SetRollerPositions(int left, int right) {
        rollers = Robot.runningRobot.rollers;
        targetL = left;
        targetR = right;
        requires(rollers);
        setTimeout(3);
    }
    
    @Override
    protected void initialize() {
        rollers.setPosition(targetL, targetR);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}

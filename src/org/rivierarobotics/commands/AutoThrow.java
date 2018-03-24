package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;
import org.rivierarobotics.subsystems.Clamp;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoThrow extends CommandGroup {

    Arm arm;
    Clamp clamp;
    double releasePos;
    double target;

    public AutoThrow(double rel) {
        arm = Robot.runningRobot.arm;
        clamp = Robot.runningRobot.clamp;
        releasePos = rel;
        requires(clamp);
    }

    @Override
    protected void initialize() {
        arm.setAngle(target);
    }

    @Override
    protected boolean isFinished() {
        return arm.getPosition() > releasePos;
    }

    @Override
    protected void end() {
        clamp.setOpen(true);
    }

}

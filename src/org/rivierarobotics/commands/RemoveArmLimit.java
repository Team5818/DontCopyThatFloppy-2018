package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RemoveArmLimit extends CommandGroup {

    public RemoveArmLimit(Joystick js) {
        this.addSequential(new Command() {

            @Override
            protected void initialize() {
                Robot.runningRobot.arm.setClimb(true);
            }

            @Override
            protected boolean isFinished() {
                return true;
            }

        });
        this.addSequential(new ArmControlClimbMode(js));

    }
}

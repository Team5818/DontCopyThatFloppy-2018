package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeaveClimbCommand extends CommandGroup {

    public LeaveClimbCommand() {
        this.addSequential(new Command() {

            @Override
            protected void initialize() {
                Robot.runningRobot.arm.setClimb(false);
            }

            @Override
            protected boolean isFinished() {
                return true;
            }

        });
        this.addSequential(new SetArmEngagedAndPTODisengaged(true));
        this.addSequential(new SetArmBrake(true));
    }
}

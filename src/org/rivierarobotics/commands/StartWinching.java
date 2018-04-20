package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartWinching extends CommandGroup{

    public class SetupWinch extends Command {

        private Arm arm;
        public SetupWinch() {
            arm = Robot.runningRobot.arm;
        }

        @Override
        protected void initialize() {
            arm.setArmEngagedAndPTODisengaged(false);
            arm.setBrakeEngaged(false);
        }

        @Override
        protected boolean isFinished() {
            return true;
        }
    }
    
    public StartWinching(Joystick js) {
        this.addSequential(new SetupWinch());
        this.addSequential(new RemoveArmLimit(js));
    }
}

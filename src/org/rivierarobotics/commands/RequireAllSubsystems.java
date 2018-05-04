package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;
import org.rivierarobotics.subsystems.Clamp;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.subsystems.Floppies;

import edu.wpi.first.wpilibj.command.Command;

public class RequireAllSubsystems extends Command {
	
	private final DriveTrain dt = Robot.runningRobot.driveTrain;
	
	private final Arm arm = Robot.runningRobot.arm;
	private final Clamp clamp = Robot.runningRobot.clamp;
	private final Floppies floppies = Robot.runningRobot.floppies;
	
	
	public RequireAllSubsystems() {
		requires(dt);
	}
	
	@Override
	protected void execute() {
		dt.setPowerLeftRight(0, 0);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}

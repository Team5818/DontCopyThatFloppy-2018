package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoPunch extends CommandGroup {

	public AutoPunch(double delay) {
		//negative delay = punch first
		if (delay > 0) {
			addSequential(new SetClampOpen(true));
			addSequential(new WaitCommand(delay));
			addSequential(new SetPuncher(true));
		} else if (delay < 0) {
			addSequential(new SetPuncher(true));
			addSequential(new WaitCommand(-delay));
			addSequential(new SetClampOpen(true));
		} else {
			addSequential(new SetPuncher(true));
			addSequential(new SetClampOpen(true));
		}
	}
}

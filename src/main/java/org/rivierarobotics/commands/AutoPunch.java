/*
 * This file is part of DontCopyThatFloppy-2018, licensed under the GNU General Public License (GPLv3).
 *
 * Copyright (c) Riviera Robotics <https://github.com/Team5818>
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
		addSequential(new WaitCommand(.5));
		addSequential(new SetPuncher(false));
	}
}

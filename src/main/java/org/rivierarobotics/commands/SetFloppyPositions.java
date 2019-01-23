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

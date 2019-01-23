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
import org.rivierarobotics.subsystems.Floppies;
import org.rivierarobotics.util.MathUtil;
import org.rivierarobotics.util.Vector2d;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class FloppyControlCommand extends Command {

    public static final Vector2d DEADBAND = new Vector2d(.2, .2);
    
    private Joystick js;
    private Floppies Floppies;
    
    public FloppyControlCommand(Joystick inputJoy) {
		js = inputJoy;
		Floppies = Robot.runningRobot.floppies;
		requires(Floppies);
    }
    
    @Override
    protected void execute() {
    	Floppies.setBrakeMode(true);
		Vector2d jsVec = MathUtil.adjustDeadband(js, DEADBAND, false);
		double inOut = jsVec.getY();
		double twist = jsVec.getX();
		Floppies.setPower(inOut - .5 * twist, inOut + .5 * twist);
		Floppies.setLightsOn(Floppies.cubeInPlace());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}

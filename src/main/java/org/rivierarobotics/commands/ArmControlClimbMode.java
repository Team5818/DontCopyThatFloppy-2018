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
import org.rivierarobotics.util.MathUtil;
import org.rivierarobotics.util.Vector2d;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ArmControlClimbMode extends Command {

    public static final Vector2d DEADBAND = new Vector2d(.05, .05);

    private Arm arm;
    private Joystick armJoy;

    public ArmControlClimbMode(Joystick joy) {
        arm = Robot.runningRobot.arm;
        armJoy = joy;
        requires(arm);
        setInterruptible(false);
    }

    @Override
    public void execute() {
        if (MathUtil.outOfDeadband(armJoy, DEADBAND)) {
            double powerVal = MathUtil.adjustDeadband(armJoy, DEADBAND, true).getY();
            arm.setPower(powerVal);
        } else {
            arm.stop();

        }
    }

    @Override
    protected boolean isFinished() {
        return !arm.isClimb();
    }

}
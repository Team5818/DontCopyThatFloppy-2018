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

import org.rivierarobotics.driverinterface.Driver;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.util.DriveCalculator;
import org.rivierarobotics.util.MathUtil;
import org.rivierarobotics.util.Vector2d;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveControlCommand extends Command {

    public static final Vector2d DEADBAND = new Vector2d(.05, .05);

    private DriveTrain dt;
    private Joystick fbStick;
    private Joystick turnStick;
    private Driver driver;

    public DriveControlCommand(Joystick fb, Joystick turn) {
        dt = Robot.runningRobot.driveTrain;
        fbStick = fb;
        turnStick = turn;
        driver = Robot.runningRobot.driver;
        requires(dt);
    }

    @Override
    public void execute() {
        if (MathUtil.outOfDeadband(fbStick, DEADBAND) || MathUtil.outOfDeadband(turnStick, DEADBAND)) {
            double forwardVal = MathUtil.adjustDeadband(fbStick, DEADBAND, true).getY();
            double spinVal = MathUtil.adjustDeadband(turnStick, DEADBAND, false).getX();
            boolean driveButtons[] = new boolean[4];
            driveButtons[0] = fbStick.getRawButton(1);
            driveButtons[1] = fbStick.getRawButton(2);
            driveButtons[2] = turnStick.getRawButton(1);
            driveButtons[3] = turnStick.getRawButton(2);
            Vector2d driveVec = driver.DRIVE_CALC.compute(new Vector2d(forwardVal, -spinVal), driveButtons);
            dt.setPowerLeftRight(driveVec);
        } else {
            dt.stop();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}

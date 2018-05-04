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
package org.rivierarobotics.subsystems;

import org.rivierarobotics.constants.RobotDependentConstants;
import org.rivierarobotics.constants.RobotMap;
import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Clamp extends Subsystem {

    private Solenoid leftPiston;
    private Solenoid rightPiston;
    private Solenoid puncher;
    private boolean isOpen;

    public Clamp() {
        leftPiston = new Solenoid(RobotMap.LEFT_CLAMP_PISTON_PORT);
        rightPiston = new Solenoid(RobotMap.RIGHT_CLAMP_PISTON_PORT);
        puncher = new Solenoid(RobotMap.PUNCHER_SOLENOID);
        isOpen = false;
    }

    public void setOpen(boolean open) {
        isOpen = open;
        leftPiston.set(open);
        rightPiston.set(open);
    }

    public void setPuncher(boolean extended) {
        if (Robot.runningRobot.arm.getPosition() < RobotDependentConstants.Constant.getPuncherMinHeight() || 
        		Robot.runningRobot.arm.getPosition() > RobotDependentConstants.Constant.getPuncherMaxHeight()) {
            extended = false;
        }
        puncher.set(extended);
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }

}
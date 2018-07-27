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

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;

/**
 * Spins, magically, using motion magic!
 */
public class MagicSpin extends Command {

    private DriveTrain dt;
    private double angle;
    private double veloc;

    public MagicSpin(double ang, double timeout) {
        setTimeout(timeout);
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        angle = ang;
    }
    
    public MagicSpin(double ang) {
        this(ang, 1.5);
    }

    @Override
    protected void initialize() {
        dt.slaveLeftToRight(true);
    }

    @Override
    protected void execute() {
        double diff = Pathfinder.boundHalfDegrees(dt.getYaw() - angle);
        double dist = Pathfinder.d2r(diff) * RobotConstants.WHEEL_BASE_WIDTH/2;
        dt.getRightSide().driveDistanceMotionMagicInches(-dist);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(Pathfinder.boundHalfDegrees(angle - dt.getYaw())) < 1
                && Math.abs(dt.getLeftSide().getSideVelocity()) < 2
                && Math.abs(dt.getRightSide().getSideVelocity()) < 2 || isTimedOut();
    }

    @Override
    protected void end() {
        dt.unslaveLeft();
        dt.stop();
    }
    
    @Override
    protected void interrupted() {
        end();
    }

}

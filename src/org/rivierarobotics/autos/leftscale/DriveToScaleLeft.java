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
package org.rivierarobotics.autos.leftscale;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class DriveToScaleLeft extends SideDependentTrajectoryExecutor {

    public static final double X_OFFSET = RobotConstants.TOTAL_ROBOT_LENGTH / 2;
    public static final double Y_OFFSET = MathUtil.feet2inches(3.68) + RobotConstants.TOTAL_ROBOT_WIDTH / 2;
    public static final double RIGHT_PLACING_X = MathUtil.feet2inches(26.5);
    public static final double RIGHT_PLACING_Y = MathUtil.feet2inches(7.0);

    public static final Waypoint[] LEFT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(27) - X_OFFSET, 0, 0) };

    public static final Waypoint[] RIGHT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(20.4) - X_OFFSET, 0, 0)};

    public DriveToScaleLeft() {
        requires(Robot.runningRobot.driveTrain);
        leftExecutor = new TrajectoryExecutor(LEFT_PATH, false, 0, DriveGear.GEAR_HIGH);
        rightExecutor = new TrajectoryExecutor(RIGHT_PATH, false, 0, DriveGear.GEAR_HIGH);
    }

    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
}

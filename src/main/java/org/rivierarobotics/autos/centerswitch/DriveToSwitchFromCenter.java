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

package org.rivierarobotics.autos.centerswitch;

import org.rivierarobotics.autos.SideDependentTrajectoryExecutor;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.util.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class DriveToSwitchFromCenter extends SideDependentTrajectoryExecutor {
    
    public static final double X_OFFSET = 0;
    public static final double Y_OFFSET = MathUtil.feet2inches(12.21);

    public static final Waypoint[] LEFT_PATH =
            new Waypoint[] { 
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(3.5) - X_OFFSET, MathUtil.feet2inches(15) - Y_OFFSET, Pathfinder.d2r(45)),
                    new Waypoint(MathUtil.feet2inches(10.33) - X_OFFSET, MathUtil.feet2inches(18) - Y_OFFSET, 0)
            };

    public static final Waypoint[] RIGHT_PATH =
            new Waypoint[] {
                    new Waypoint(0, 0, 0), 
                    new Waypoint(MathUtil.feet2inches(2) - X_OFFSET, MathUtil.feet2inches(12.21) - Y_OFFSET, 0),
                    new Waypoint(MathUtil.feet2inches(5) - X_OFFSET, MathUtil.feet2inches(10) - Y_OFFSET, Pathfinder.d2r(-45)),
                    new Waypoint(MathUtil.feet2inches(8.5) - X_OFFSET,  MathUtil.feet2inches(9) - Y_OFFSET, 0),
                    new Waypoint(MathUtil.feet2inches(10.33) - X_OFFSET, MathUtil.feet2inches(9) - Y_OFFSET, 0)
            };



    public DriveToSwitchFromCenter() {
        requires(Robot.runningRobot.driveTrain);
        leftExecutor = new TrajectoryExecutor(LEFT_PATH, false,0);
        rightExecutor = new TrajectoryExecutor(RIGHT_PATH, false,0);
    }

    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[0] == Side.RIGHT;
    }
}

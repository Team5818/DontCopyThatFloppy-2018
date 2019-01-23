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

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class DriveDownfieldFromSwitch extends SideDependentTrajectoryExecutor {

    public static final double X_OFFSET = MathUtil.feet2inches(10.33);
    public static final double Y_OFFSET_LEFT = MathUtil.feet2inches(18);
    public static final double Y_OFFSET_RIGHT = MathUtil.feet2inches(9);
    
    public static final Waypoint[] LEFT_PATH = new Waypoint[] { 
            new Waypoint(0, 0, 0),
            new Waypoint(X_OFFSET - MathUtil.feet2inches(8.0), Y_OFFSET_LEFT - MathUtil.feet2inches(20.5), Pathfinder.d2r(-90)),
            new Waypoint(X_OFFSET - MathUtil.feet2inches(14.0), Y_OFFSET_LEFT - MathUtil.feet2inches(23.0), Pathfinder.d2r(-180)),
            new Waypoint(X_OFFSET - MathUtil.feet2inches(16.0), Y_OFFSET_LEFT - MathUtil.feet2inches(23.0), Pathfinder.d2r(-180)) };

    public static final Waypoint[] RIGHT_PATH = new Waypoint[] { 
            new Waypoint(0, 0, 0),
            new Waypoint(X_OFFSET - MathUtil.feet2inches(8.0), Y_OFFSET_RIGHT - MathUtil.feet2inches(6.5), Pathfinder.d2r(90)),
            new Waypoint(X_OFFSET - MathUtil.feet2inches(14.0), Y_OFFSET_RIGHT - MathUtil.feet2inches(4.0), Pathfinder.d2r(180)),
            new Waypoint(X_OFFSET - MathUtil.feet2inches(16.0), Y_OFFSET_RIGHT - MathUtil.feet2inches(4.0), Pathfinder.d2r(180)) };

    public DriveDownfieldFromSwitch() {
        requires(Robot.runningRobot.driveTrain);
        leftExecutor = new TrajectoryExecutor(LEFT_PATH,true,0);
        rightExecutor = new TrajectoryExecutor(RIGHT_PATH,true,0);
    }

    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[0] == Side.RIGHT;
    }

}

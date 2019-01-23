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

package org.rivierarobotics.constants;

import edu.wpi.first.wpilibj.Preferences;

/**
 * Constants class. Implemented by different instances for different robots.
 */
public abstract class RobotDependentConstants {

    private static final String DEFAULT = "competition";
    /**
     * The constant instance for this robot.
     */
    public static final RobotDependentConstants Constant;
    static {
        String robotId = Preferences.getInstance().getString("robot-id", DEFAULT);
        switch (robotId) {
            case "competition":
                Constant = new CompetitionRobotSpecificConstants();
                break;
            case "practice":
                Constant = new PracticeRobotSpecificConstants();
                break;
            default:
                throw new IllegalStateException("Illegal robot ID!!");
        }
    }

    public double getArmPositionScaleHigh() {
        return getLowerArmSoftLimit() + 1521;
    }
    
    public double getArmPositionThrowRelease() {
        return getLowerArmSoftLimit() + 371;
    }

    public double getArmPositionSwitchMid() {
        return getLowerArmSoftLimit() + 543;
    }

    public double getArmPositionCollectStandby() {
        return getLowerArmSoftLimit() + 281;
    }

    public double getArmPositionGrabbing() {
        return getLowerArmSoftLimit() + 45;
    }
    
    public double getArmPositionBack() {
        return getLowerArmSoftLimit() + 1796;
    }
    
    public double getUpperArmSoftLimit() {
        return getLowerArmSoftLimit() + 1850;
    }
    
    public double getPuncherMinHeight() {
    	return getLowerArmSoftLimit() + 1077;
    }
    
    public double getPuncherMaxHeight() {
    	return getLowerArmSoftLimit() + 1731;
    }
    
    public abstract double getLowerArmSoftLimit();

}

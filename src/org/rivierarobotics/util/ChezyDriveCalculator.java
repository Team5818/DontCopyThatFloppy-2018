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
package org.rivierarobotics.util;

public class ChezyDriveCalculator implements DriveCalculator{
    
    private static ChezyDriveCalculator INSTANCE = new ChezyDriveCalculator();
    
    private ChezyDriveCalculator() {}
    
    public static ChezyDriveCalculator getInstance() {
        return INSTANCE;
    }
    
    /**
     * Chezy Drive
     */
    @Override
    public Vector2d compute(Vector2d input, boolean...buttons) {

        double wheel = input.getX();
        double throttle = input.getY();
        boolean isQuickTurn = buttons[2];
        double overPower;
        double angularPower;

        if (isQuickTurn) {
            overPower = 1.0 - throttle * 5;
            angularPower = wheel;
        } else {
            overPower = 0.0;
            angularPower = Math.abs(throttle) * wheel;
        }
        double rightPwm = throttle + angularPower;
        double leftPwm = throttle - angularPower;
        if (leftPwm > 1.0) {
            rightPwm -= overPower * (leftPwm - 1.0);
            leftPwm = 1.0;
        } else if (rightPwm > 1.0) {
            leftPwm -= overPower * (rightPwm - 1.0);
            rightPwm = 1.0;
        } else if (leftPwm < -1.0) {
            rightPwm += overPower * (-leftPwm - 1.0);
            leftPwm = -1.0;
        } else if (rightPwm < -1.0) {
            leftPwm += overPower * (-rightPwm - 1.0);
            rightPwm = -1.0;
        }
        if (Math.abs(leftPwm) > 1.0 || Math.abs(rightPwm) > 1.0) {
            leftPwm = MathUtil.limit(leftPwm, 1);
            rightPwm = MathUtil.limit(rightPwm, 1);
        }

        Vector2d output = new Vector2d(leftPwm, rightPwm);
        return output;
    }
    
}

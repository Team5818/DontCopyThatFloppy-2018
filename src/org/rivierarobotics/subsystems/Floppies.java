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

import org.rivierarobotics.commands.FloppyControlCommand;
import org.rivierarobotics.constants.RobotMap;
import org.rivierarobotics.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Floppies extends Subsystem {

    private WPI_TalonSRX left;
    private WPI_TalonSRX right;
    private static int PID_IDX = 0;
    private static int SLOT_IDX = 0;
    private static int TIMEOUT = 10;
    public static int LEFT_ZERO_POS = 3886;
    public static int RIGHT_ZERO_POS = 3254;
    public DigitalInput leftSwitch;
    public DigitalInput rightSwitch;
    private Solenoid lights;

    public Floppies() {
        left = new WPI_TalonSRX(RobotMap.LEFT_ROLLER_TALON);
        right = new WPI_TalonSRX(RobotMap.RIGHT_ROLLER_TALON);
        lights = new Solenoid(RobotMap.LIGHTS_SOLENOID_PORT);
        left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, PID_IDX, TIMEOUT);
        right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, PID_IDX, TIMEOUT);
        right.setInverted(true);
        left.setSensorPhase(true);
        right.setSensorPhase(true);
        left.selectProfileSlot(SLOT_IDX, PID_IDX);
        right.selectProfileSlot(SLOT_IDX, PID_IDX);

        left.config_kP(SLOT_IDX, 0.0006 * 1023, TIMEOUT);
        left.config_kI(SLOT_IDX, 0.000004 * 1023, TIMEOUT);
        left.config_kD(SLOT_IDX, 0.0, TIMEOUT);

        right.config_kP(SLOT_IDX, 0.0006 * 1023, TIMEOUT);
        right.config_kI(SLOT_IDX, 0.000004 * 1023, TIMEOUT);
        right.config_kD(SLOT_IDX, 0.0, TIMEOUT);

        leftSwitch = new DigitalInput(RobotMap.LEFT_LIMIT_SWITCH_PORT);
        rightSwitch = new DigitalInput(RobotMap.RIGHT_LIMIT_SWITCH_PORT);
    }
    
    public void setLightsOn(boolean on) {
        lights.set(on);
    }

    public void setPower(double leftPow, double rightPow) {
        left.set(leftPow);
        right.set(rightPow);
    }

    public boolean cubeInPlace()// gets switch data
    {
        return (!leftSwitch.get() || !rightSwitch.get());
    }
    
    public boolean leftSwitchAcitve() {
        return !leftSwitch.get();
    }

    public boolean rightSwitchActive() {
        return !rightSwitch.get();
    }
    
    public void setBrakeMode(boolean on) {
        if (on) {
            left.setNeutralMode(NeutralMode.Brake);
            right.setNeutralMode(NeutralMode.Brake);
        } else {
            left.setNeutralMode(NeutralMode.Coast);
            right.setNeutralMode(NeutralMode.Coast);
        }
    }

    public void setPosition(int leftPos, int rightPos) {
        int leftSet = (getLeftPos() & 0xFFFFF000) + leftPos;
        int rightSet = (getRightPos() & 0xFFFFF000) + rightPos;
        left.set(ControlMode.Position, leftSet);
        right.set(ControlMode.Position, rightSet);
    }

    public int getLeftPos() {
        return left.getSelectedSensorPosition(PID_IDX);
    }

    public int getRightPos() {
        return right.getSelectedSensorPosition(PID_IDX);
    }

    public int getLeftTrunc() {
        return getLeftPos() & 0xFFF;
    }

    public int getRightTrunc() {
        return getRightPos() & 0xFFF;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new FloppyControlCommand(Robot.runningRobot.driver.JS_FLOPPIES));
    }
}

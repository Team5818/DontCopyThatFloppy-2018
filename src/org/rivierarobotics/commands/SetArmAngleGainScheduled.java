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
import org.rivierarobotics.subsystems.Clamp;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SetArmAngleGainScheduled extends CommandGroup{
        
    public class StopArm extends Command{
        private Arm arm = Robot.runningRobot.arm;
        
        public StopArm(){
            requires(arm);
            setTimeout(.1);
        }

        @Override
        protected void initialize() {
            arm.stop();
        }
        
        @Override
        protected boolean isFinished() {
            return isTimedOut();
        }

    }
    
    public class MoveArm extends Command{
        private Arm arm = Robot.runningRobot.arm;
        private Clamp clamp = Robot.runningRobot.clamp;
        private double target;
        
        public MoveArm(double ang, double time) {
            target = ang;
            setTimeout(time);
            requires(arm);
        }
        
        @Override
        protected void initialize() {
            Arm.ArmMotionState motionState;
            if(target > arm.getPosition()) {
                if(clamp.isOpen()) {
                    motionState = Arm.ArmMotionState.UP_NO_CUBE;
                }
                else {
                    motionState = Arm.ArmMotionState.UP_WITH_CUBE;
                }
            }
            else {
                if(clamp.isOpen()) {
                    motionState = Arm.ArmMotionState.DOWN_NO_CUBE;
                }
                else {
                    motionState = Arm.ArmMotionState.DOWN_WITH_CUBE;
                }
            }
            arm.setAngle(target,motionState);
        }
    
        @Override
        protected boolean isFinished() {
            return isTimedOut();
        }
    }
    
    public SetArmAngleGainScheduled(double ang, double time) {
        this.addSequential(new StopArm());
        this.addSequential(new MoveArm(ang, time));
    }
    
    public SetArmAngleGainScheduled(double ang) {
        this(ang, 0);
    }
}

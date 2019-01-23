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

import org.rivierarobotics.constants.RobotDependentConstants;
import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class CollectGrabRaise extends CommandGroup{
    
    public class ConfigForDrop extends Command{

        @Override
        protected void initialize(){
            Robot.runningRobot.arm.configureForCollect();
        }
        
        @Override
        protected boolean isFinished() {
            return true;
        }
        
    }
    
    public class UnSlam extends Command{

        @Override
        protected void initialize(){
            Robot.runningRobot.arm.configureForNormalMotion();
        }
        
        @Override
        protected boolean isFinished() {
            return true;
        }
        
    }
        
    public CollectGrabRaise(boolean isTeleop) {
        CommandGroup collectGroup = new CommandGroup();
        CommandGroup lowerGroup = new CommandGroup();
        lowerGroup.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionCollectStandby()));
        if(isTeleop) {
            lowerGroup.addParallel(new WaitForCube(10, 0.0, false));//allow driver to control floppies
        }else {
            lowerGroup.addParallel(new WaitForCube(10, 1.0, true));//set power directly
        }
        collectGroup.addSequential(lowerGroup);
        collectGroup.addSequential(new TimedCommand(.5));
        this.addSequential(new SetClampOpen(true));
        this.addSequential(collectGroup);
        this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionGrabbing(),.6));
        this.addSequential(new SetClampOpen(false));
        if(isTeleop) {
            this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
        }
        if(!isTeleop) {
            this.addSequential(new SetFloppyPower(0.0));
        }
    }
}

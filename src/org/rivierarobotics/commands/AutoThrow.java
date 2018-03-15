package org.rivierarobotics.commands;

import org.rivierarobotics.constants.RobotDependentConstants;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;
import org.rivierarobotics.subsystems.Clamp;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class AutoThrow extends CommandGroup{
    public class Fling extends Command{
        Arm arm;
        Clamp clamp;
        
        public Fling() {
            arm = Robot.runningRobot.arm;
            clamp = Robot.runningRobot.clamp;
            requires(arm);
            requires(clamp);
        }
        
        @Override
        protected void initialize() {
            arm.configreForCubeThrow();
            arm.setAngle(RobotDependentConstants.Constant.getArmPositionScaleHigh());
        }
        
        @Override
        protected void execute() {
            if(arm.getPosition() > RobotDependentConstants.Constant.getArmPositionCollectStandby() + 90) {
                clamp.setOpen(true);
            }
        }
        
        @Override
        protected boolean isFinished() {
            return arm.getPosition() > RobotDependentConstants.Constant.getArmPositionScaleHigh();
        }
        
        @Override
        protected void end() {
            clamp.setOpen(true);
            arm.configureForNormalMotion();
        }
    }
    
    public AutoThrow() {
//        CommandGroup lowerGroup = new CommandGroup();
//        lowerGroup.addParallel(new TimedCommand(1));
//        lowerGroup.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionGrabbing()));
//        this.addSequential(lowerGroup);
        this.addSequential(new Fling());
    }

}
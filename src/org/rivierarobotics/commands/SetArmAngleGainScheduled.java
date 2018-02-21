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

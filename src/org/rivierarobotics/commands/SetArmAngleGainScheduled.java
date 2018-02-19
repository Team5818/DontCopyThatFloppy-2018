package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;
import org.rivierarobotics.subsystems.Clamp;

import edu.wpi.first.wpilibj.command.Command;

public class SetArmAngleGainScheduled extends Command{
    Arm arm = Robot.runningRobot.arm;
    Clamp clamp = Robot.runningRobot.clamp;
    double target;
    
    public SetArmAngleGainScheduled(double ang) {
        target = ang;
        requires(arm);
        setTimeout(3);
    }
    
    @Override
    protected void initialize() {
        Arm.ArmMotionState motionState;
        if(target > arm.getPosition()) {
            if(!clamp.isOpen()) {
                motionState = Arm.ArmMotionState.UP_WITH_CUBE;
            }
            else {
                motionState = Arm.ArmMotionState.UP_NO_CUBE;
            }
        }
        else {
            if(!clamp.isOpen()) {
                motionState = Arm.ArmMotionState.DOWN_WITH_CUBE;
            }
            else {
                motionState = Arm.ArmMotionState.DOWN_NO_CUBE;
            }
        }
        arm.setAngle(target,motionState);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}

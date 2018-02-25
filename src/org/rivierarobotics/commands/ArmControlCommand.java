package org.rivierarobotics.commands;

import org.rivierarobotics.mathUtil.MathUtil;
import org.rivierarobotics.mathUtil.Vector2d;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ArmControlCommand extends Command {

    public static final Vector2d DEADBAND = new Vector2d(.05, .05);

    private Arm arm;
    private Joystick armJoy;

    public ArmControlCommand(Joystick joy) {
        arm = Robot.runningRobot.arm;
        armJoy = joy;
        requires(arm);
    }

    @Override
    public void execute() {
        if (MathUtil.outOfDeadband(armJoy, DEADBAND)){
            double powerVal = MathUtil.adjustDeadband(armJoy, DEADBAND, true).getY();
            arm.setPower(powerVal);
        } else {
            if(!arm.isProfileInProgress()) {
                arm.stop();
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}

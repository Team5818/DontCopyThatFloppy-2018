package org.rivierarobotics.commands;

import org.rivierarobotics.constants.RobotDependentConstants;
import org.rivierarobotics.mathUtil.MathUtil;
import org.rivierarobotics.mathUtil.Vector2d;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Arm;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class ArmControlCommand extends Command {

    public static final Vector2d DEADBAND = new Vector2d(.05, .05);
    public static final double UPPER_RAMP_ZONE_WIDTH = 700;
    public static final double LOWER_RAMP_ZONE_WIDTH = 700;
    public static final double FULL_OPEN_LOOP_ZONE = 350;
    public static final double SMALL_VELOCITY = 30;

    private Arm arm;
    private Joystick armJoy;
    private double lastPosition;

    public ArmControlCommand(Joystick joy) {
        arm = Robot.runningRobot.arm;
        armJoy = joy;
        requires(arm);
        lastPosition = Double.NaN;
    }

    @Override
    public void execute() {
        if (MathUtil.outOfDeadband(armJoy, DEADBAND)) {
            double powerVal = MathUtil.adjustDeadband(armJoy, DEADBAND, true).getY();
            if (arm.getPosition() > RobotDependentConstants.Constant.getUpperArmSoftLimit()) {
                powerVal = Math.min(powerVal, 0);
            }
            if (arm.getPosition() < RobotDependentConstants.Constant.getLowerArmSoftLimit()) {
                powerVal = Math.max(powerVal, 0);
            } else if (RobotDependentConstants.Constant.getUpperArmSoftLimit()
                    - arm.getPosition() < UPPER_RAMP_ZONE_WIDTH) {
                powerVal =
                        Math.min(powerVal, (RobotDependentConstants.Constant.getUpperArmSoftLimit() - arm.getPosition())
                                / UPPER_RAMP_ZONE_WIDTH);
            } else if (arm.getPosition()
                    - RobotDependentConstants.Constant.getLowerArmSoftLimit() < LOWER_RAMP_ZONE_WIDTH) {
                powerVal = Math.max(powerVal,
                        -(arm.getPosition() - RobotDependentConstants.Constant.getLowerArmSoftLimit())
                                / LOWER_RAMP_ZONE_WIDTH);
            }
            arm.setPower(powerVal);
            lastPosition = arm.getPosition();
        } else {
            if (!arm.isProfileInProgress() && !Double.isNaN(lastPosition)
                    && Math.abs(arm.getVelocity()) < SMALL_VELOCITY
                    && arm.getPosition() > RobotDependentConstants.Constant.getArmPositionGrabbing() + FULL_OPEN_LOOP_ZONE) {
                arm.setAngle(lastPosition, Arm.ArmMotionState.DOWN_NO_CUBE);
            } else {
                lastPosition = arm.getPosition();
                if (!arm.isProfileInProgress()) {
                    arm.stop();
                }
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}

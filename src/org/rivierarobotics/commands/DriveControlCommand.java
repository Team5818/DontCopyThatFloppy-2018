package org.rivierarobotics.commands;

import org.rivierarobotics.driverinterface.Driver;
import org.rivierarobotics.mathUtil.DriveCalculator;
import org.rivierarobotics.mathUtil.MathUtil;
import org.rivierarobotics.mathUtil.Vector2d;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class DriveControlCommand extends Command {

    public static final Vector2d DEADBAND = new Vector2d(.05, .05);

    private DriveTrain dt;
    private Joystick fbStick;
    private Joystick turnStick;
    private Driver driver;

    public DriveControlCommand(Joystick fb, Joystick turn) {
        dt = Robot.runningRobot.driveTrain;
        fbStick = fb;
        turnStick = turn;
        driver = Robot.runningRobot.driver;
        requires(dt);
    }

    @Override
    public void execute() {
        if (MathUtil.outOfDeadband(fbStick, DEADBAND) || MathUtil.outOfDeadband(turnStick, DEADBAND)) {
            double forwardVal = MathUtil.adjustDeadband(fbStick, DEADBAND, true).getY();
            double spinVal = MathUtil.adjustDeadband(turnStick, DEADBAND, false).getX();
            boolean driveButtons[] = new boolean[4];
            driveButtons[0] = fbStick.getRawButton(1);
            driveButtons[1] = fbStick.getRawButton(2);
            driveButtons[2] = turnStick.getRawButton(1);
            driveButtons[3] = turnStick.getRawButton(2);
            Vector2d driveVec = driver.DRIVE_CALC.compute(new Vector2d(forwardVal, -spinVal), driveButtons);
            dt.setPowerLeftRight(driveVec);
        } else {
            dt.stop();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}

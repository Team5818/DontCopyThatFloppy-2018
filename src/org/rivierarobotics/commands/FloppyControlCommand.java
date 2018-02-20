package org.rivierarobotics.commands;

import org.rivierarobotics.mathUtil.MathUtil;
import org.rivierarobotics.mathUtil.Vector2d;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Floppies;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class FloppyControlCommand extends Command {

    public static final Vector2d DEADBAND = new Vector2d(.2, .2);
    
    private Joystick js;
    private Floppies Floppies;
    
    public FloppyControlCommand(Joystick inputJoy) {
		js = inputJoy;
		Floppies = Robot.runningRobot.floppies;
		requires(Floppies);
    }
    
    @Override
    protected void execute() {
    	Floppies.setBrakeMode(true);
		Vector2d jsVec = MathUtil.adjustDeadband(js, DEADBAND, false);
		double inOut = jsVec.getY();
		double twist = jsVec.getX();
		Floppies.setPower(inOut - .5 * twist, inOut + .5 * twist);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}

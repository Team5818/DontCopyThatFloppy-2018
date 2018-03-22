package org.rivierarobotics.commands;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;

/**
 * Spins, magically, using motion magic!
 */
public class MagicSpin extends Command {

    private DriveTrain dt;
    private double angle;
    private double veloc;

    public MagicSpin(double ang) {
        setTimeout(1.5);
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        angle = ang;
    }

    @Override
    protected void initialize() {
        dt.slaveLeftToRight(true);
    }

    @Override
    protected void execute() {
        double diff = Pathfinder.boundHalfDegrees(dt.getYaw() - angle);
        double dist = Pathfinder.d2r(diff) * RobotConstants.WHEEL_BASE_WIDTH/2;
        dt.getRightSide().driveDistanceMotionMagicInches(-dist);
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(Pathfinder.boundHalfDegrees(angle - dt.getYaw())) < 1
                && Math.abs(dt.getLeftSide().getSideVelocity()) < 2
                && Math.abs(dt.getRightSide().getSideVelocity()) < 2 || isTimedOut();
    }

    @Override
    protected void end() {
        dt.unslaveLeft();
        dt.stop();
    }
    
    @Override
    protected void interrupted() {
        end();
    }

}

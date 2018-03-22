package org.rivierarobotics.autos;
import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;

public abstract class SideDependentSpin extends Command{

    protected double leftTarget;
    protected double rightTarget;
    protected DriveTrain dt;
    double selectedTarget;
    
    protected abstract boolean isRightSide();
    
    @Override
    protected void initialize() {
        if(isRightSide()) {
            selectedTarget = rightTarget;
        }
        else {
            selectedTarget = leftTarget;
        }
        if(!Double.isNaN(selectedTarget)) {
            dt.slaveLeftToRight(true);
        }
    }
    
    @Override
    protected void execute() {
        if(!Double.isNaN(selectedTarget)) {
            double diff = Pathfinder.boundHalfDegrees(dt.getYaw() - selectedTarget);
            double dist = Pathfinder.d2r(diff) * RobotConstants.WHEEL_BASE_WIDTH/2;
            dt.getRightSide().driveDistanceMotionMagicInches(-dist);
        }
    }
    
    @Override
    protected boolean isFinished() {
        if(Double.isNaN(selectedTarget)) {
            DriverStation.reportError("we done spinning", false);
            return true;
        }
        return Math.abs(Pathfinder.boundHalfDegrees(selectedTarget - dt.getYaw())) < 1
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

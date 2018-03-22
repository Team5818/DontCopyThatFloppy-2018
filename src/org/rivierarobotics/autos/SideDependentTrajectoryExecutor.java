package org.rivierarobotics.autos;

import org.rivierarobotics.constants.Side;
import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;

public abstract class SideDependentTrajectoryExecutor extends Command{

    protected TrajectoryExecutor leftExecutor;
    protected TrajectoryExecutor rightExecutor;
    private TrajectoryExecutor selectedExecutor;
    
    protected abstract boolean isRightSide();
    
    @Override
    protected void initialize() {
        if(isRightSide()) {
            selectedExecutor = rightExecutor;
        }
        else {
            selectedExecutor = leftExecutor;
        }
        if(selectedExecutor != null) {
            selectedExecutor.start();
        }
    }
    
    @Override
    protected boolean isFinished() {
        if(selectedExecutor == null) {
            return true;
        }
        return selectedExecutor.isFinished();
    }
    
    @Override
    protected void interrupted() {
        if(selectedExecutor != null) {
            selectedExecutor.stop();
        }
    }
}

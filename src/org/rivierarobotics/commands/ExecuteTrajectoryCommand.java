package org.rivierarobotics.commands;

import org.rivierarobotics.pathfollowing.TrajectoryExecutor;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;

public class ExecuteTrajectoryCommand extends Command{
    private TrajectoryExecutor trajEx;
    private DriveTrain dt;
    
    public ExecuteTrajectoryCommand(Waypoint[] wp, boolean reversed, boolean flip) {
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        trajEx = new TrajectoryExecutor(wp,reversed, flip);
    }
    
    @Override
    protected void initialize() {
        trajEx.start();
    }

    @Override
    protected boolean isFinished() {
        return trajEx.isFinished();
    }
    
    @Override
    protected void interrupted() {
        trajEx.stop();
    }
}

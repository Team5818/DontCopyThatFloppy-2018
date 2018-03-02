package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class SetCameraCollect extends Command{
    
    private boolean collect;
    
    public SetCameraCollect(boolean c){
        collect = c;
    }
    
    @Override
    protected void initialize() {
        if(collect) {
            Robot.runningRobot.camServer.setSource(Robot.runningRobot.camCollect);
        }
        else {
            Robot.runningRobot.camServer.setSource(Robot.runningRobot.camBack);
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}

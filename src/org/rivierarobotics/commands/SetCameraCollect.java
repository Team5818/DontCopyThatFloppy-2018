package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetCameraCollect extends Command{
    
    private boolean collect;
    
    public SetCameraCollect(boolean c){
        collect = c;
    }
    
    protected void initalize() {
        String camName;
        if(collect) {
            camName = Robot.runningRobot.camCollect.getName();
        }
        else {
            camName = Robot.runningRobot.camBack.getName();
        }
        SmartDashboard.putString("Cmaera Selection", camName);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}

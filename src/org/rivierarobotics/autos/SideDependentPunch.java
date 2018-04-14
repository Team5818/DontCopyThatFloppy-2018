package org.rivierarobotics.autos;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SideDependentPunch extends Command{

    private Side side;
    public SideDependentPunch(Side s) {
        side = s;
        setTimeout(.2);
    }
    
    protected void initialize() {
        if(Robot.runningRobot.getSide()[1] == side) {
            Robot.runningRobot.clamp.setPuncher(true);
        }
    }
    
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return isTimedOut();
    }
}

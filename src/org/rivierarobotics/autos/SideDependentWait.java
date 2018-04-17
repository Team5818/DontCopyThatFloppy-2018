package org.rivierarobotics.autos;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;

import edu.wpi.first.wpilibj.command.Command;

public class SideDependentWait extends Command{

    private Side side;
    private Double secs;
    public SideDependentWait(Side s, double t) {
        side = s;
        secs = t;
    }
    
    protected void initialize() {
        if(Robot.runningRobot.getSide()[1] == side) {
            setTimeout(secs);
        }
        else {
            setTimeout(0);
        }
    }
    
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}

package org.rivierarobotics.autos;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;

import edu.wpi.first.wpilibj.command.Command;

public class SideDependentShift extends Command{

    private Side side;
    private DriveGear gear;
    public SideDependentShift(Side s, DriveGear g) {
        side = s;
        gear = g;
    }
    
    protected void initialize() {
        if(Robot.runningRobot.getSide()[1] == side) {
            Robot.runningRobot.driveTrain.shiftGear(gear);
        }
    }
    
    @Override
    protected boolean isFinished() {
        return true;
    }
}

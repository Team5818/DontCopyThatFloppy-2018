package org.rivierarobotics.autos.twocuberightscale;

import org.rivierarobotics.autos.SideDependentSpin;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

public class LeftSideOnlyTurnToCross2R extends SideDependentSpin{

    public LeftSideOnlyTurnToCross2R() {
        leftTarget = 90.0;
        rightTarget = Double.NaN;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(.75);
    }
    
    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
    
}

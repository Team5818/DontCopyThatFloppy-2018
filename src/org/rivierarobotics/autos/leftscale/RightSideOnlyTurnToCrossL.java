package org.rivierarobotics.autos.leftscale;

import org.rivierarobotics.autos.SideDependentSpin;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

public class RightSideOnlyTurnToCrossL extends SideDependentSpin{

    public RightSideOnlyTurnToCrossL() {
        leftTarget = -90.0;
        rightTarget = -90.0;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(.75);
    }
    
    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
    
}

package org.rivierarobotics.autos.baselinescale;

import org.rivierarobotics.autos.SideDependentSpin;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

public class RightSideOnlyTurnToCrossBL extends SideDependentSpin{

    public RightSideOnlyTurnToCrossBL() {
        leftTarget = Double.NaN;
        rightTarget = -90.0;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(1);
    }
    
    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
    
}

package org.rivierarobotics.autos.baselinescale;

import org.rivierarobotics.autos.SideDependentSpin;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

public class RightSideOnlyLastSpinBL extends SideDependentSpin{

    public RightSideOnlyLastSpinBL() {
        leftTarget = Double.NaN;
        rightTarget = 90.0;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(.75);
    }
    
    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
    
}

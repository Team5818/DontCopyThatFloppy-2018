package org.rivierarobotics.autos.leftscale;

import org.rivierarobotics.autos.SideDependentSpin;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

public class RIghtSideOnlySpinToScaleL extends SideDependentSpin{

    public RIghtSideOnlySpinToScaleL() {
        rightTarget = 0.0;
        leftTarget = Double.NaN;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(.75);
    }
    
    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
    
}

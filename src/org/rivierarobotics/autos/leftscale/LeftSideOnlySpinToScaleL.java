package org.rivierarobotics.autos.leftscale;

import org.rivierarobotics.autos.SideDependentSpin;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

public class LeftSideOnlySpinToScaleL extends SideDependentSpin{

    public LeftSideOnlySpinToScaleL() {
        leftTarget = 0.0;
        rightTarget = Double.NaN;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(1);
    }
    
    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
    
}

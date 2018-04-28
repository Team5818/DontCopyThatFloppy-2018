package org.rivierarobotics.autos.twocubeleftscale;

import org.rivierarobotics.autos.SideDependentSpin;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

public class RightSideOnlySpinToScale2L extends SideDependentSpin{

    public RightSideOnlySpinToScale2L() {
        leftTarget = Double.NaN;
        rightTarget = 0.0;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(.75);
    }
    
    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
    
}

package org.rivierarobotics.autos.twocubeleftscale;

import org.rivierarobotics.autos.SideDependentSpin;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

public class LeftSideOnlySpinToScale2L extends SideDependentSpin{

    public LeftSideOnlySpinToScale2L() {
        leftTarget = 0.0;
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

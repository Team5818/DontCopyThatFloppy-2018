package org.rivierarobotics.autos.rightscale;

import org.rivierarobotics.autos.SideDependentSpin;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

public class LeftSideOnlyTurnToCross extends SideDependentSpin{

    public LeftSideOnlyTurnToCross() {
        leftTarget = 90.0;
        rightTarget = 90.0;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(1);
    }
    
    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
    
}

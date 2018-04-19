package org.rivierarobotics.autos.twocuberightscale;

import org.rivierarobotics.autos.SideDependentSpin;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

public class TurnToCube2R extends SideDependentSpin{

    public TurnToCube2R() {
        leftTarget = 180;
        rightTarget = 120;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(1);
    }
    
    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
    
}

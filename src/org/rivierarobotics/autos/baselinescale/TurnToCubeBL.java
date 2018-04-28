package org.rivierarobotics.autos.baselinescale;

import org.rivierarobotics.autos.SideDependentSpin;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;

public class TurnToCubeBL extends SideDependentSpin{

    public TurnToCubeBL() {
        rightTarget = -180;
        leftTarget = -120;
        dt = Robot.runningRobot.driveTrain;
        requires(dt);
        setTimeout(1);
    }
    
    @Override
    protected boolean isRightSide() {
        return Robot.runningRobot.getSide()[1] == Side.RIGHT;
    }
    
}

package org.rivierarobotics.constants;

public class PracticeRobotSpecificConstants extends RobotDependentConstants {

    @Override
    public double getArmPositionScaleHigh() {
        return 3712;
    }

    @Override
    public double getArmPositionScaleLow() {
        return 980;
    }

    @Override
    public double getArmPositionSwitchMid() {
        return 2881;
    }

    @Override
    public double getArmPositionCollectStandby() {
        return 150;
    }

    @Override
    public double getArmPositionGrabbing() {
        return 4087;
    }

    @Override
    public double getArmPositionBack() {
        return 2390;
    }

}

package org.rivierarobotics.constants;

public class PracticeRobotSpecificConstants extends RobotDependentConstants {

    @Override
    public double getArmPositionScaleHigh() {
        return 3712;
    }

    @Override
    public double getArmPositionScaleLow() {
        return -254;//wrong
    }

    @Override
    public double getArmPositionSwitchMid() {
        return 2881;
    }

    @Override
    public double getArmPositionCollectStandby() {
        return -254;//wrong
    }

    @Override
    public double getArmPositionGrabbing() {
        return 2390;
    }

    @Override
    public double getArmPositionBack() {
        return 4087;
    }

    @Override
    public double getUpperArmSoftLimit() {
        return 4170;
    }

    @Override
    public double getLowerArmSoftLimit() {
        return 2356;
    }

}

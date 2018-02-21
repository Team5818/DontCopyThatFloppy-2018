package org.rivierarobotics.constants;

public class PracticeRobotSpecificConstants extends RobotDependentConstants {

    @Override
    public double getArmPositionScaleHigh() {
        return 1300;
    }

    @Override
    public double getArmPositionScaleLow() {
        return 980;
    }

    @Override
    public double getArmPositionSwitchMid() {
        return 490;
    }

    @Override
    public double getArmPositionCollectStandby() {
        return 150;
    }

    @Override
    public double getArmPositionGrabbing() {
        return -55;
    }

}

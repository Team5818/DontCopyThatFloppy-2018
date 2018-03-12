package org.rivierarobotics.constants;

public class PracticeRobotSpecificConstants extends RobotDependentConstants {

    @Override
    public double getArmPositionScaleHigh() {
        return 4870;
    }

    @Override
    public double getArmPositionThrowRelease() {
        return 4460;
    }


    @Override
    public double getArmPositionSwitchMid() {
        return 3892;
    }

    @Override
    public double getArmPositionCollectStandby() {
        return 3630;
    }

    @Override
    public double getArmPositionGrabbing() {
        return 3394;
    }

    @Override
    public double getArmPositionBack() {
        return 5145;
    }

    @Override
    public double getUpperArmSoftLimit() {
        return 5252;
    }

    @Override
    public double getLowerArmSoftLimit() {
        return 3349;
    }

}

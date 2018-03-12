package org.rivierarobotics.constants;


public class CompetitionRobotSpecificConstants extends RobotDependentConstants{

    @Override
    public double getArmPositionScaleHigh() {
        return 4260;
    }

    @Override
    public double getArmPositionThrowRelease() {
        return -254;
    }

    @Override
    public double getArmPositionSwitchMid() {
        return 3360;
    }

    @Override
    public double getArmPositionCollectStandby() {
        return 2975;
    }

    @Override
    public double getArmPositionGrabbing() {
        return 2800;
    }

    @Override
    public double getArmPositionBack() {
        return 4500;//guess
    }

    @Override
    public double getUpperArmSoftLimit() {
        return getArmPositionBack();
    }

    @Override
    public double getLowerArmSoftLimit() {
        return getArmPositionGrabbing();
    }

}

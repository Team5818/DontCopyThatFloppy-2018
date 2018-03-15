package org.rivierarobotics.constants;

import edu.wpi.first.wpilibj.Preferences;

/**
 * Constants class. Implemented by different instances for different robots.
 */
public abstract class RobotDependentConstants {

    private static final String DEFAULT = "competition";
    /**
     * The constant instance for this robot.
     */
    public static final RobotDependentConstants Constant;
    static {
        String robotId = Preferences.getInstance().getString("robot-id", DEFAULT);
        switch (robotId) {
            case "competition":
                Constant = new CompetitionRobotSpecificConstants();
                break;
            case "practice":
                Constant = new PracticeRobotSpecificConstants();
                break;
            default:
                throw new IllegalStateException("Illegal robot ID!!");
        }
    }

    public double getArmPositionScaleHigh() {
        return getLowerArmSoftLimit() + 1521;
    }
    
    public double getArmPositionThrowRelease() {
        return getLowerArmSoftLimit() + 371;
    }

    public double getArmPositionSwitchMid() {
        return getLowerArmSoftLimit() + 543;
    }

    public double getArmPositionCollectStandby() {
        return getLowerArmSoftLimit() + 281;
    }

    public double getArmPositionGrabbing() {
        return getLowerArmSoftLimit() + 45;
    }
    
    public double getArmPositionBack() {
        return getLowerArmSoftLimit() + 1796;
    }
    
    public double getUpperArmSoftLimit() {
        return getLowerArmSoftLimit() + 1850;
    }
    
    public abstract double getLowerArmSoftLimit();

}

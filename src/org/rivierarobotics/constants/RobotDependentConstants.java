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

    public abstract double getArmPositionScaleHigh();

    public abstract double getArmPositionScaleLow();

    public abstract double getArmPositionSwitchMid();

    public abstract double getArmPositionCollectStandby();

    public abstract double getArmPositionGrabbing();
    
    public abstract double getArmPositionBack();
    
    public abstract double getUpperArmSoftLimit();
    
    public abstract double getLowerArmSoftLimit();

}

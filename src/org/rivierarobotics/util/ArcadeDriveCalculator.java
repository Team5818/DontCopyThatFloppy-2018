package org.rivierarobotics.util;


public class ArcadeDriveCalculator implements DriveCalculator{
    
    private static ArcadeDriveCalculator INSTANCE = new ArcadeDriveCalculator();
    
    private ArcadeDriveCalculator() {}
    
    public static ArcadeDriveCalculator getInstance() {
        return INSTANCE;
    }
    
    /**
     * Standard arcade drive
     */
    @Override
    public Vector2d compute(Vector2d in, boolean...buttons) {
        double moveValue = in.getX();
        double rotateValue = in.getY();
        double leftMotorSpeed;
        double rightMotorSpeed;
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        return new Vector2d(leftMotorSpeed, rightMotorSpeed);
    }
    
}

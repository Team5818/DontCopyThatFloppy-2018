package org.rivierarobotics.subsystems;

import org.rivierarobotics.commands.DriveControlCommand;
import org.rivierarobotics.constants.RobotMap;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.util.Vector2d;
import org.rivierarobotics.constants.RobotMap;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for drivetrain. Most logic takes place in DriveTrainSide
 */
public class DriveTrain extends Subsystem {

    private DriveTrainSide left;
    private DriveTrainSide right;
    public Solenoid shifter;
    private PigeonIMU gyro;

    public enum DriveGear{
        GEAR_LOW, GEAR_HIGH
    }
    
    public DriveTrain() {
        gyro = new PigeonIMU(RobotMap.GYRO_ID);
        left = new DriveTrainSide(Side.LEFT);
        right = new DriveTrainSide(Side.RIGHT);
        shifter = new Solenoid(RobotMap.SHIFTER_SOLENOID);
        setBrakeMode();
    }

    public void shiftGear(DriveGear gear) {
        if(gear == DriveGear.GEAR_LOW) {
            shifter.set(false);
        }
        else {
            shifter.set(true);
        }
    }
    
    public void setPowerLeftRight(double lpow, double rpow) {
        left.setPower(lpow);
        right.setPower(rpow);
    }

    public void setPowerLeftRight(Vector2d vec2) {
        setPowerLeftRight(vec2.getX(), vec2.getY());
    }

    public DriveTrainSide getLeftSide() {
        return left;
    }

    public DriveTrainSide getRightSide() {
        return right;
    }
    
    public double getYaw() {
        double[] ypr = {0,0,0};
        gyro.getYawPitchRoll(ypr);
        return ypr[0];
    }

    public void resetGyro() {
        gyro.setYaw(0.0, 10);
    }
    
    public void driveDistance(double leftDist, double rightDist) {
        left.driveDistanceMotionMagicInches(leftDist);
        right.driveDistanceMotionMagicInches(rightDist);
    }
    
    public void resetEnc() {
        left.resetEnc();
        right.resetEnc();
    }
    
    public Vector2d getDistance() {
        return new Vector2d(left.getSidePosition(), right.getSidePosition());
    }
    
    public Vector2d getDistanceInches() {
        return new Vector2d(left.getSidePositionInches(), right.getSidePositionInches());
    }
    
    public Vector2d getVelocity() {
        return new Vector2d(left.getSideVelocity(), right.getSideVelocity());
    }
    
    public Vector2d getVelocityIPS() {
        return new Vector2d(left.getSideVelocityIPS(), right.getSideVelocityIPS());
    }
    
    public double getAvgSidePosition() {
        return (left.getSidePosition() + right.getSidePosition()) / 2;
    }
    
    public double getAvgSidePositionInches() {
        return (left.getSidePositionInches() + right.getSidePositionInches())/2;
    }
    
    public double getAvgSideVelocity() {
        return (left.getSideVelocity() + right.getSideVelocity()) / 2;
    }
    
    public void slaveLeftToRight(boolean inverted) {
        left.slaveToOtherSide(inverted);
    }
    
    public void unslaveLeft() {
        left.unslave();
    }

    public void setCoastMode() {
        left.setCoastMode();
        right.setCoastMode();
    }

    public void setBrakeMode() {
        left.setBrakeMode();
        right.setBrakeMode();
    }

    public void setPowerStraight(double numIn) {
        left.setPower(numIn);
        right.setPower(numIn);
    }

    public void stop() {
        left.setBrakeMode();
        right.setBrakeMode();
        this.setPowerLeftRight(0, 0);
    }

    @Override
    protected void initDefaultCommand() {
        /* driving logic is here */
        setDefaultCommand(
                new DriveControlCommand(Robot.runningRobot.driver.JS_FW_BACK, Robot.runningRobot.driver.JS_TURN));
    }

}
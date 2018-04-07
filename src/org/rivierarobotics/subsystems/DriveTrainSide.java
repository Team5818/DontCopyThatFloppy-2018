package org.rivierarobotics.subsystems;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.RobotMap;
import org.rivierarobotics.constants.Side;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Subsystem representing one side of the drive base. 
 */
public class DriveTrainSide {

    private static final int MAGIC_VEL = 575;
    private static final int MAGIC_ACCEL = 800;
    private static final int MOTION_MAGIC_IDX = 0;
    private static final int SLOT_IDX = 0;
    private static final int TIMEOUT = 10;
    public static final double DIST_PER_REV = RobotConstants.WHEEL_DIAMETER * Math.PI;
    public static final int ENCODER_CODES_PER_REV = 360;
    
    private WPI_TalonSRX slaveTalon1;
    private WPI_TalonSRX slaveTalon2;
    private WPI_TalonSRX masterTalon;
    private Side mySide;
    private double masterID;

    public DriveTrainSide(Side side) {
        /* Instantiate different components depending on side */
        mySide = side;
        if (side == Side.CENTER) {
            throw new IllegalArgumentException("A drive side may not be in the center");
        }
        if (side == Side.RIGHT) {
            masterID = RobotMap.RIGHT_DRIVETRAIN_TALON_1;
            masterTalon = new WPI_TalonSRX(RobotMap.RIGHT_DRIVETRAIN_TALON_1);
            slaveTalon1 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVETRAIN_TALON_2);
            slaveTalon2 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVETRAIN_TALON_3);
            masterTalon.setInverted(false);
            slaveTalon1.setInverted(true);
            slaveTalon2.setInverted(true);

        } else {
            masterID = RobotMap.LEFT_DRIVETRAIN_TALON_1;
            masterTalon = new WPI_TalonSRX(RobotMap.LEFT_DRIVETRAIN_TALON_1);
            slaveTalon1 = new WPI_TalonSRX(RobotMap.LEFT_DRIVETRAIN_TALON_2);
            slaveTalon2 = new WPI_TalonSRX(RobotMap.LEFT_DRIVETRAIN_TALON_3);
            masterTalon.setInverted(true);
            slaveTalon1.setInverted(false);
            slaveTalon2.setInverted(false);
        }
        
        masterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        masterTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT);
        masterTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TIMEOUT);
        masterTalon.selectProfileSlot(SLOT_IDX, MOTION_MAGIC_IDX);
        masterTalon.config_kF(SLOT_IDX, 0.00138 * 1023.0, TIMEOUT);
        masterTalon.config_kP(SLOT_IDX, 0.003 * 1023.0, TIMEOUT);
        masterTalon.config_kI(SLOT_IDX, 0.0, TIMEOUT);
        masterTalon.config_kD(SLOT_IDX, 0.0, TIMEOUT);
        masterTalon.configMotionCruiseVelocity(MAGIC_VEL, TIMEOUT);
        masterTalon.configMotionAcceleration(MAGIC_ACCEL, TIMEOUT);
        
        resetEnc();
        masterTalon.set(ControlMode.PercentOutput, 0.0);
        slaveTalon1.set(ControlMode.Follower, masterID);
        slaveTalon2.set(ControlMode.Follower, masterID);
    }

    public void setPower(double numIn) {
        masterTalon.set(ControlMode.PercentOutput, numIn);
        
    }

    public void driveDistanceMotionMagicInches(double dist) {
        double ticks = dist/DIST_PER_REV*ENCODER_CODES_PER_REV*4.0;
        DriverStation.reportError(""+ticks,false);
        double setPoint = masterTalon.getSelectedSensorPosition(MOTION_MAGIC_IDX) + ticks;
        masterTalon.set(ControlMode.MotionMagic, setPoint);
    }
    
    public void slaveToOtherSide(boolean inverted) {
        if(mySide == Side.LEFT) {
            masterTalon.set(ControlMode.Follower, RobotMap.RIGHT_DRIVETRAIN_TALON_1);
            slaveTalon1.set(ControlMode.Follower, RobotMap.RIGHT_DRIVETRAIN_TALON_1);
            slaveTalon2.set(ControlMode.Follower, RobotMap.RIGHT_DRIVETRAIN_TALON_1);
            masterTalon.setInverted(true ^ inverted);
            slaveTalon1.setInverted(false ^ inverted);
            slaveTalon2.setInverted(false ^ inverted);
        }
        if(mySide == Side.RIGHT) {
            masterTalon.set(ControlMode.Follower, RobotMap.LEFT_DRIVETRAIN_TALON_1);
            slaveTalon1.set(ControlMode.Follower, RobotMap.LEFT_DRIVETRAIN_TALON_1);
            slaveTalon2.set(ControlMode.Follower, RobotMap.LEFT_DRIVETRAIN_TALON_1);
            masterTalon.setInverted(false ^ inverted);
            slaveTalon1.setInverted(true ^ inverted);
            slaveTalon2.setInverted(true ^ inverted);
        }
    }
    
    public void unslave() {
        if(mySide == Side.LEFT) {
            masterTalon.set(ControlMode.PercentOutput, 0.0);
            slaveTalon1.set(ControlMode.Follower, RobotMap.LEFT_DRIVETRAIN_TALON_1);
            slaveTalon2.set(ControlMode.Follower, RobotMap.LEFT_DRIVETRAIN_TALON_1);
            masterTalon.setInverted(true);
            slaveTalon1.setInverted(false);
            slaveTalon2.setInverted(false);
        }
        if(mySide == Side.RIGHT) {
            masterTalon.set(ControlMode.PercentOutput, 0.0);
            slaveTalon1.set(ControlMode.Follower, RobotMap.LEFT_DRIVETRAIN_TALON_1);
            slaveTalon2.set(ControlMode.Follower, RobotMap.LEFT_DRIVETRAIN_TALON_1);
            masterTalon.setInverted(false);
            slaveTalon1.setInverted(true);
            slaveTalon2.setInverted(true);
        }
    }


    public double getSidePosition() {
        return masterTalon.getSelectedSensorPosition(0);
    }
    
    public double getSidePositionInches() {
        return (double)(masterTalon.getSelectedSensorPosition(0))/(double)(ENCODER_CODES_PER_REV)*DIST_PER_REV/4.0;
    }

    public double getRawPos() {
        return masterTalon.getSensorCollection().getQuadraturePosition();
    }

    public double getSideVelocity() {
        return masterTalon.getSelectedSensorVelocity(0);
    }
    
    public double getSideVelocityIPS() {
        return masterTalon.getSelectedSensorVelocity(0)/(double)(ENCODER_CODES_PER_REV)*DIST_PER_REV/4.0*10;
    }
    
    public double getRawSpeed() {
        return masterTalon.getSensorCollection().getQuadratureVelocity();
    }
    
    public void resetEnc() {
        masterTalon.setSelectedSensorPosition(0, 0, 10);
    }
    
    public void setCoastMode() {
        masterTalon.setNeutralMode(NeutralMode.Coast);
        slaveTalon1.setNeutralMode(NeutralMode.Coast);
        slaveTalon2.setNeutralMode(NeutralMode.Coast);
    }

    public void setBrakeMode() {
        masterTalon.setNeutralMode(NeutralMode.Brake);
        slaveTalon1.setNeutralMode(NeutralMode.Brake);
        slaveTalon2.setNeutralMode(NeutralMode.Brake);
    }
}
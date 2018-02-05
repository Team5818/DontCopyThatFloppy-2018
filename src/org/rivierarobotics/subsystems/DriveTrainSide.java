package org.rivierarobotics.subsystems;

import org.rivierarobotics.constants.RobotConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Subsystem representing one side of the drive base. 
 */
public class DriveTrainSide {

    public static final double DIST_PER_REV = RobotConstants.WHEEL_DIAMETER * Math.PI;
    public static final int ENCODER_CODES_PER_REV = 1024;
    private WPI_TalonSRX slaveTalon1;
    private WPI_TalonSRX slaveTalon2;
    private WPI_TalonSRX masterTalon;
    private double masterID;

    public DriveTrainSide(Side side) {
        /* Instantiate different components depending on side */
        if (side == Side.CENTER) {
            throw new IllegalArgumentException("A drive side may not be in the center");
        }
        if (side == Side.RIGHT) {
            masterID = RobotMap.RIGHT_DRIVETRAIN_TALON_1;
            masterTalon = new WPI_TalonSRX(RobotMap.RIGHT_DRIVETRAIN_TALON_1);
            slaveTalon1 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVETRAIN_TALON_2);
            slaveTalon2 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVETRAIN_TALON_3);
            masterTalon.setInverted(true);
            slaveTalon1.setInverted(false);
            slaveTalon2.setInverted(false);

        } else {
            masterID = RobotMap.LEFT_DRIVETRAIN_TALON_1;
            masterTalon = new WPI_TalonSRX(RobotMap.LEFT_DRIVETRAIN_TALON_1);
            slaveTalon1 = new WPI_TalonSRX(RobotMap.LEFT_DRIVETRAIN_TALON_2);
            slaveTalon2 = new WPI_TalonSRX(RobotMap.LEFT_DRIVETRAIN_TALON_3);
            masterTalon.setInverted(false);
            slaveTalon1.setInverted(true);
            slaveTalon2.setInverted(true);
        }
        masterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        masterTalon.set(ControlMode.PercentOutput, 0.0);
        slaveTalon1.set(ControlMode.Follower, masterID);
        slaveTalon2.set(ControlMode.Follower, masterID);
    }

    public void setPower(double numIn) {
        masterTalon.set(ControlMode.PercentOutput, numIn);
    }


    public double getSidePosition() {
        return masterTalon.getSelectedSensorPosition(0);
    }

    public double getRawPos() {
        return masterTalon.getSensorCollection().getQuadraturePosition();
    }

    public double getSideVelocity() {
        return masterTalon.getSelectedSensorVelocity(0);
    }
    
    public double getRawSpeed() {
        return masterTalon.getSensorCollection().getQuadratureVelocity();
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
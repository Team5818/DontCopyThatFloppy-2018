package org.rivierarobotics.subsystems;

import org.rivierarobotics.robot.RobotMap;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {

    private static final int MAX_VELOCITY = 135;
    private static final int MOTION_MAGIC_IDX = 0;
    private static final int SLOT_IDX = 0;
    private static final int TIMEOUT = 10;
    
    public static final int ARM_POSITION_SCALE_HIGH = 0;
    public static final int ARM_POSIITON_SCALE_LOW = 0;
    public static final int ARM_POSITION_MID = 0;
    public static final int ARM_POSITION_COLLECT_STANDBY = 0;
    public static final int ARM_POSITION_GRABBING = 0;

    private WPI_TalonSRX masterTalon;
    private WPI_TalonSRX slaveTalon1;
    private WPI_TalonSRX slaveTalon2;

    public Arm() {
        masterTalon = new WPI_TalonSRX(RobotMap.ARM_TALON_1);
        slaveTalon1 = new WPI_TalonSRX(RobotMap.ARM_TALON_2);
        slaveTalon2 = new WPI_TalonSRX(RobotMap.ARM_TALON_3);
        slaveTalon1.set(ControlMode.Follower, RobotMap.ARM_TALON_1);
        slaveTalon2.set(ControlMode.Follower, RobotMap.ARM_TALON_1);
        masterTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, MOTION_MAGIC_IDX, TIMEOUT);
        masterTalon.setSensorPhase(false);
        masterTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT);
        masterTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TIMEOUT);
        masterTalon.selectProfileSlot(SLOT_IDX, MOTION_MAGIC_IDX);
        masterTalon.config_kF(SLOT_IDX, 0.0, TIMEOUT);// 1023 / MAX_VELOCITY, TIMEOUT);
        masterTalon.config_kP(SLOT_IDX, 0.0, TIMEOUT);//0.006 * 1023, TIMEOUT);
        masterTalon.config_kI(SLOT_IDX, 0.0, TIMEOUT);
        masterTalon.config_kD(SLOT_IDX, 0.0, TIMEOUT);//0.04 * 1023, TIMEOUT);
        masterTalon.configMotionCruiseVelocity(MAX_VELOCITY / 2, TIMEOUT);
        masterTalon.configMotionAcceleration(MAX_VELOCITY / 2, TIMEOUT);
        setBrakeMode();
    }

    public void setPower(double pow) {
        masterTalon.set(ControlMode.PercentOutput, pow);
    }

    public double getPower() {
        return masterTalon.getMotorOutputPercent();
    }

    public double getPosition() {
        return masterTalon.getSelectedSensorPosition(MOTION_MAGIC_IDX);
    }

    public double getRawPosition() {
        return masterTalon.getSensorCollection().getPulseWidthPosition();
    }

    public double getVelocity() {
        return masterTalon.getSelectedSensorVelocity(MOTION_MAGIC_IDX);
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

    public void stop() {
        setPower(0.0);
        setBrakeMode();
    }

    @Override
    protected void initDefaultCommand() {
        //setDefaultCommand(new ArmControlCommand(Robot.runningRobot.driver.JS_ARM));
    }

    public void setAngle(double angle) {
        masterTalon.set(ControlMode.MotionMagic, angle);
    }
}

package org.rivierarobotics.subsystems;

import org.rivierarobotics.commands.ArmControlCommand;
import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.constants.RobotDependentConstants;
import org.rivierarobotics.constants.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {

    public enum ArmMotionState {
        UP_WITH_CUBE, UP_NO_CUBE, DOWN_WITH_CUBE, DOWN_NO_CUBE
    }
    private static final int MAX_POSSIBLE_VELOCITY = 400;
    private static final int MOTION_MAGIC_IDX = 0;
    private static final int SLOT_IDX = 0;
    private static final int TIMEOUT = 10;

    public static final double KF_UP_WITH_CUBE = 1023.0 / 307.27;
    public static final double KF_UP_NO_CUBE = 1023.0 / 342.045;
    public static final double KF_DOWN_WITH_CUBE = 1023.0 / 450.00;
    public static final double KF_DOWN_NO_CUBE = 1023.0 / 428.86;

    private WPI_TalonSRX masterTalon;
    private WPI_TalonSRX slaveTalon1;
    private WPI_TalonSRX slaveTalon2;

    private Solenoid armPTOSolenoid;
    private Solenoid armEngagerSolenoid;
    private Solenoid armBrakeSolenoid;
    private boolean climbMode = false;

    public Arm() {
        masterTalon = new WPI_TalonSRX(RobotMap.ARM_TALON_1);
        slaveTalon1 = new WPI_TalonSRX(RobotMap.ARM_TALON_2);
        slaveTalon2 = new WPI_TalonSRX(RobotMap.ARM_TALON_3);
        slaveTalon1.set(ControlMode.Follower, RobotMap.ARM_TALON_1);
        slaveTalon2.set(ControlMode.Follower, RobotMap.ARM_TALON_1);
        masterTalon.setInverted(false);
        slaveTalon1.setInverted(true);
        slaveTalon2.setInverted(true);
        masterTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, MOTION_MAGIC_IDX, TIMEOUT);
        masterTalon.setSensorPhase(false);
        masterTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT);
        masterTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TIMEOUT);
        masterTalon.selectProfileSlot(SLOT_IDX, MOTION_MAGIC_IDX);
        masterTalon.config_kF(SLOT_IDX, KF_UP_NO_CUBE, TIMEOUT);
        masterTalon.config_kP(SLOT_IDX, 0.002 * 1023, TIMEOUT);
        masterTalon.config_kI(SLOT_IDX, 0.0, TIMEOUT);
        masterTalon.config_kD(SLOT_IDX, 0.015 * 1023, TIMEOUT);
        masterTalon.configMotionCruiseVelocity((int) (MAX_POSSIBLE_VELOCITY / 2.5), TIMEOUT);
        masterTalon.configMotionAcceleration((int) (MAX_POSSIBLE_VELOCITY / 1.5), TIMEOUT);
        setBrakeMode();
        armPTOSolenoid = new Solenoid(RobotMap.ARM_PTO_SOLENOID);
        armEngagerSolenoid = new Solenoid(RobotMap.ARM_ENGAGER_SOLENOID);
        armBrakeSolenoid = new Solenoid(RobotMap.ARM_BRAKE_SOLENOID);
    }

    public void setArmEngaged(boolean engaged) {
        armEngagerSolenoid.set(!engaged);
    }
    
    public void setPTOEngaged(boolean engaged) {
        armPTOSolenoid.set(engaged);
    }
    
    public void setBrakeEngaged(boolean engaged) {
        armBrakeSolenoid.set(!engaged);;
    }
    
    public void setPower(double pow) {
        masterTalon.set(ControlMode.PercentOutput, pow);
    }

    public double getPower() {
        return masterTalon.getMotorOutputPercent();
    }

    public double getPosition() {
        int pos = masterTalon.getSelectedSensorPosition(MOTION_MAGIC_IDX);
        if(pos < RobotDependentConstants.Constant.getLowerArmSoftLimit() - 200) {
            masterTalon.setSelectedSensorPosition(pos + 4096, MOTION_MAGIC_IDX, TIMEOUT);
        }
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
    
    public boolean isClimb() {
        return climbMode;
    }
    
    //PLEASE BE CAUTIOUS ABOUT CALLING THIS!!!
    public void setClimb(boolean climb) {
        climbMode = climb;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ArmControlCommand(Robot.runningRobot.driver.JS_ARM));
    }

    public void configureMotionState(ArmMotionState state) {
        switch (state) {
            case UP_WITH_CUBE:
                masterTalon.config_kF(SLOT_IDX, KF_UP_WITH_CUBE, TIMEOUT);
                break;
            case UP_NO_CUBE:
                masterTalon.config_kF(SLOT_IDX, KF_UP_NO_CUBE, TIMEOUT);
                break;
            case DOWN_WITH_CUBE:
                masterTalon.config_kF(SLOT_IDX, KF_DOWN_WITH_CUBE, TIMEOUT);
                break;
            case DOWN_NO_CUBE:
                masterTalon.config_kF(SLOT_IDX, KF_DOWN_NO_CUBE, TIMEOUT);
        }
    }

    public void setAngle(double angle) {
        masterTalon.set(ControlMode.MotionMagic, angle);
    }
    
    public boolean isProfileInProgress() {
        return masterTalon.getControlMode().equals(ControlMode.MotionMagic); 
    }

    public void setAngle(double angle, ArmMotionState state) {
        configureMotionState(state);
        setAngle(angle);
    }

    public int getClosedLoopTarget() {
        return masterTalon.getActiveTrajectoryPosition();
    }
    
    public int getClosedLoopError() {
        return masterTalon.getClosedLoopError(MOTION_MAGIC_IDX);
    }
}

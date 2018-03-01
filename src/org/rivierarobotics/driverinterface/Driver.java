package org.rivierarobotics.driverinterface;

import org.rivierarobotics.commands.SetArmAngleGainScheduled;
import org.rivierarobotics.commands.SetArmBrake;
import org.rivierarobotics.commands.SetCameraCollect;
import org.rivierarobotics.commands.SetClampOpen;
import org.rivierarobotics.commands.ShiftGear;
import org.rivierarobotics.commands.StartClimbMode;
import org.rivierarobotics.constants.ControlMap;
import org.rivierarobotics.constants.RobotDependentConstants;
import org.rivierarobotics.mathUtil.ArcadeDriveCalculator;
import org.rivierarobotics.mathUtil.DriveCalculator;
import org.rivierarobotics.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Driver {

    public Joystick JS_FW_BACK;
    public Joystick JS_TURN;
    public Joystick JS_ARM;
    public Joystick JS_FLOPPIES;
    public Joystick JS_LEFT_BUTTONS;
    public Joystick JS_RIGHT_BUTTONS;
    public JoystickButton forceCompressorOn;
    public DriveCalculator DRIVE_CALC;

    public Driver() {
        // Instantiate Sticks
        JS_FW_BACK = new Joystick(ControlMap.FW_BACK_JOYSTICK_PORT);
        JS_TURN = new Joystick(ControlMap.TURN_JOYSTICK_PORT);
        JS_ARM = new Joystick(ControlMap.ARM_JOYSTICK_PORT);
        JS_FLOPPIES = new Joystick(ControlMap.TBD_JOYSTICK_PORT);
        JS_LEFT_BUTTONS = new Joystick(ControlMap.LEFT_BUTTONS_PORT);
        JS_RIGHT_BUTTONS = new Joystick(ControlMap.RIGHT_BUTTONS_PORT);
        DRIVE_CALC = ArcadeDriveCalculator.getInstance();

        // Instantiate Buttons
        JoystickButton clampOn = new JoystickButton(JS_ARM, ControlMap.CLAMP_ON_BUTTON);
        JoystickButton clampOff = new JoystickButton(JS_ARM, ControlMap.CLAMP_OFF_BUTTON);
        JoystickButton setArmAngleButtonHigh =
                new JoystickButton(JS_LEFT_BUTTONS, ControlMap.SET_ARM_ANGLE_BUTTON_HIGH);
        JoystickButton setArmAngleButtonMid = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.SET_ARM_ANGLE_BUTTON_MID);
        JoystickButton setArmAngleButtonLow = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.SET_ARM_ANGLE_BUTTON_LOW);
        JoystickButton setArmAngleButtonBack =
                new JoystickButton(JS_LEFT_BUTTONS, ControlMap.SET_ARM_ANGLE_BUTTON_BACK);
        JoystickButton shiftLow = new JoystickButton(JS_FW_BACK, ControlMap.SHIFT_LOW_BUTTON);
        JoystickButton shiftHigh = new JoystickButton(JS_FW_BACK, ControlMap.SHIFT_HIGH_BUTTON);
        JoystickButton backCamButton = new JoystickButton(JS_TURN, ControlMap.BACK_CAM_BUTTON);
        JoystickButton collectCamButton = new JoystickButton(JS_TURN, ControlMap.COLLECT_CAM_BUTTON);
        forceCompressorOn = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.FORCE_COMPRESSOR_ON_BUTTON);

        JoystickButton enterClimbButton = new JoystickButton(JS_RIGHT_BUTTONS, ControlMap.CLIMB_MODE_BUTTON);
        JoystickButton reengageArmButton = new JoystickButton(JS_RIGHT_BUTTONS, ControlMap.REENGAGE_ARM_BUTTON);
        JoystickButton disengageArmButton = new JoystickButton(JS_RIGHT_BUTTONS, ControlMap.DISENGAGE_ARM_BUTTON);
        JoystickButton lockWinchButton = new JoystickButton(JS_RIGHT_BUTTONS, ControlMap.LOCK_WINCH_BUTTON);

        JoystickButton enterClimbButton = new JoystickButton(JS_RIGHT_BUTTONS, ControlMap.CLIMB_MODE_BUTTON);
        JoystickButton reengageArmButton = new JoystickButton(JS_RIGHT_BUTTONS, ControlMap.REENGAGE_ARM_BUTTON);
        JoystickButton disengageArmButton = new JoystickButton(JS_RIGHT_BUTTONS, ControlMap.DISENGAGE_ARM_BUTTON);
        JoystickButton lockWinchButton = new JoystickButton(JS_RIGHT_BUTTONS, ControlMap.LOCK_WINCH_BUTTON);

        // Bind Commands
        clampOn.whenPressed(new SetClampOpen(false));
        clampOff.whenPressed(new SetClampOpen(true));
        setArmAngleButtonHigh
                .whenPressed(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionScaleHigh()));
        setArmAngleButtonMid
                .whenPressed(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
        setArmAngleButtonLow
                .whenPressed(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionGrabbing()));
        setArmAngleButtonBack
                .whenPressed(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionBack()));
        shiftLow.whenPressed(new ShiftGear(DriveTrain.DriveGear.GEAR_LOW));
        shiftHigh.whenPressed(new ShiftGear(DriveTrain.DriveGear.GEAR_HIGH));
        backCamButton.whenPressed(new SetCameraCollect(false));
        collectCamButton.whenPressed(new SetCameraCollect(true));

        enterClimbButton.whenPressed(new StartClimbMode(JS_ARM));//engage PTO + disengage arm
        reengageArmButton.whenPressed(new SetArmEngaged(true));//reengage + lower arm, winching at same time
        disengageArmButton.whenPressed(new SetArmEngaged(false));//disengage arm when it hits bottom
        lockWinchButton.whenPressed(new SetArmBrake(true));//lock robot in place after climb

    }
}

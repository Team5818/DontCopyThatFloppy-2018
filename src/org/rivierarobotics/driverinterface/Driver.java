package org.rivierarobotics.driverinterface;

import org.rivierarobotics.commands.AutoPunch;
import org.rivierarobotics.commands.AutoThrow;
import org.rivierarobotics.commands.CollectGrabRaise;
import org.rivierarobotics.commands.CompressorControlCommand;
import org.rivierarobotics.commands.LeaveClimbCommand;
import org.rivierarobotics.commands.MagicSpin;
import org.rivierarobotics.commands.SetArmAngleGainScheduled;
import org.rivierarobotics.commands.SetArmBrake;
import org.rivierarobotics.commands.SetArmEngagedAndPTODisengaged;
import org.rivierarobotics.commands.SetClampOpen;
import org.rivierarobotics.commands.SetDrivePower;
import org.rivierarobotics.commands.ShiftGear;
import org.rivierarobotics.commands.StartWinching;
import org.rivierarobotics.commands.RemoveArmLimit;
import org.rivierarobotics.constants.ControlMap;
import org.rivierarobotics.constants.RobotDependentConstants;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.util.ArcadeDriveCalculator;
import org.rivierarobotics.util.DriveCalculator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Driver {

    public Joystick JS_FW_BACK;
    public Joystick JS_TURN;
    public Joystick JS_ARM;
    public Joystick JS_FLOPPIES;
    public Joystick JS_LEFT_BUTTONS;
    public Joystick JS_RIGHT_BUTTONS;
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

        JoystickButton removeArmLimitButton = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.REMOVE_ARM_LIMIT_BUTTON);
        JoystickButton startWinchingButton = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.START_WINCHING_BUTTON);
        JoystickButton reengageArmButton = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.REENGAGE_ARM_BUTTON);
        JoystickButton disengageArmButton = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.DISENGAGE_ARM_BUTTON);
        JoystickButton lockWinchButton = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.LOCK_WINCH_BUTTON);
        JoystickButton unlockWinchButton = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.UNLOCK_WINCH_BUTTON);
        JoystickButton leaveClimbButton = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.LEAVE_CLIMB_BUTTON);
        
        JoystickButton autoCollectButton = new JoystickButton(JS_FLOPPIES, ControlMap.COLLECT_SEQUENCE_BUTTON);
        JoystickButton autoPunch = new JoystickButton(JS_FLOPPIES, ControlMap.AUTO_PUNCH_BUTTON);

        
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
        //autoCollectButton.whenPressed(new CollectGrabRaise(true));

        removeArmLimitButton.whenPressed(new RemoveArmLimit(JS_ARM));//engage PTO + disengage arm
        startWinchingButton.whenPressed(new StartWinching());
        reengageArmButton.whenPressed(new SetArmEngagedAndPTODisengaged(true));//reengage + lower arm, winching at same time
        disengageArmButton.whenPressed(new SetArmEngagedAndPTODisengaged(false));//disengage arm when it hits bottom
        lockWinchButton.whenPressed(new SetArmBrake(true));//lock robot in place after climb   
        unlockWinchButton.whenPressed(new SetArmBrake(false));//for the pits
        leaveClimbButton.whenPressed(new LeaveClimbCommand());//for crisis mode
        
        autoPunch.whenPressed(new AutoPunch(-0.2));
    }
}

package org.rivierarobotics.driverinterface;

import org.rivierarobotics.commands.SetArmPower;
import org.rivierarobotics.commands.SetFloppyPositions;
import org.rivierarobotics.constants.ControlMap;
import org.rivierarobotics.mathUtil.ArcadeDriveCalculator;
import org.rivierarobotics.mathUtil.DriveCalculator;
import org.rivierarobotics.subsystems.Floppies;

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
        //Instantiate Sticks
        JS_FW_BACK = new Joystick(ControlMap.FW_BACK_JOYSTICK_PORT);
        JS_TURN = new Joystick(ControlMap.TURN_JOYSTICK_PORT);
        JS_ARM = new Joystick(ControlMap.ARM_JOYSTICK_PORT);
        JS_FLOPPIES = new Joystick(ControlMap.TBD_JOYSTICK_PORT);
        JS_LEFT_BUTTONS = new Joystick(ControlMap.LEFT_BUTTONS_PORT);
        JS_RIGHT_BUTTONS = new Joystick(ControlMap.RIGHT_BUTTONS_PORT);
        DRIVE_CALC = ArcadeDriveCalculator.getInstance();
        
        //Instantiate Buttons
//        JoystickButton clampOn = new JoystickButton(JS_LEFT_BUTTONS,ControlMap.CLAMP_ON_BUTTON);
//        JoystickButton clampOff = new JoystickButton(JS_LEFT_BUTTONS,ControlMap.CLAMP_OFF_BUTTON);
//        JoystickButton setArmAngleButtonHigh = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.SET_ARM_ANGLE_BUTTON_HIGH);
//        JoystickButton setArmAngleButtonMid = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.SET_ARM_ANGLE_BUTTON_MID);
//        JoystickButton setArmAngleButtonLow = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.SET_ARM_ANGLE_BUTTON_LOW);
          JoystickButton zeroFlapsButton = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.ZERO_FLAPPYS_BUTTON);
          JoystickButton setArmPowerButton = new JoystickButton(JS_LEFT_BUTTONS, ControlMap.SET_ARM_ANGLE_BUTTON_MID);
//        
//        //Bind Commands
//        clampOn.whenPressed(new SetClampOpenCommand(false));
//        clampOff.whenPressed(new SetClampOpenCommand(true));
//        setArmAngleButtonHigh.whenPressed(new SetArmAngle(700));
//        setArmAngleButtonMid.whenPressed(new SetArmAngle(485));
//        setArmAngleButtonLow.whenPressed(new SetArmAngle(250));
          setArmPowerButton.whileHeld(new SetArmPower(.2));
          zeroFlapsButton.whenPressed(new SetFloppyPositions(Floppies.LEFT_ZERO_POS, Floppies.RIGHT_ZERO_POS));
    }    
}

package org.rivierarobotics.commands;

import org.rivierarobotics.constants.ControlMap;
import org.rivierarobotics.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class CompressorControlCommand extends Command {

    private double minVoltage;
    private Joystick joy;

    public CompressorControlCommand(double voltage, Joystick js) {
        minVoltage = voltage;
        joy = js;
    }

    public CompressorControlCommand(Joystick js) {
        this(10.0, js);
    }

    protected void execute() {
        if (determineCompressorOn())
            Robot.runningRobot.compressor.start();
        else
            Robot.runningRobot.compressor.stop();
    }

    private boolean determineCompressorOn() {
        if (joy.getRawButton(ControlMap.FORCE_COMPRESSOR_ON_BUTTON))
            return true;
        if (Robot.runningRobot.pdp.getVoltage() > minVoltage)
            return true;
        return false;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

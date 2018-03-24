package org.rivierarobotics.commands;

import org.rivierarobotics.robot.Robot;
import org.rivierarobotics.subsystems.Floppies;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForCube extends Command {

    private boolean runFloppies;
    private double power;
    private Floppies flop;

    public WaitForCube(double time, double pow, boolean runFlops) {
        setTimeout(time);
        runFloppies = runFlops;
        power = pow;
        flop = Robot.runningRobot.floppies;
        if (runFloppies) {
            requires(flop);
        }
    }

    @Override
    protected void initialize() {
        if (runFloppies) {
            flop.setPower(power, power);
        }
    }

    @Override
    protected boolean isFinished() {
        return Robot.runningRobot.floppies.cubeInPlace() || isTimedOut();
    }

}

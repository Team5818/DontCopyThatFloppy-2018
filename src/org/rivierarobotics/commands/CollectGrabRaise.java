package org.rivierarobotics.commands;

import org.rivierarobotics.constants.RobotDependentConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class CollectGrabRaise extends CommandGroup{
        
    public CollectGrabRaise(boolean isTeleop) {
        CommandGroup collectGroup = new CommandGroup();
        CommandGroup lowerGroup = new CommandGroup();
        lowerGroup.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionCollectStandby()));
        if(isTeleop) {
            lowerGroup.addParallel(new WaitForCube(10, 0.0, false));//allow driver to control floppies
        }else {
            lowerGroup.addParallel(new WaitForCube(10, 1.0, true));//set power directly
        }
        collectGroup.addSequential(lowerGroup);
        collectGroup.addSequential(new TimedCommand(.5));
        if(!isTeleop) {
            collectGroup.addSequential(new SetFloppyPower(0.0));
        }
        this.addSequential(new SetClampOpen(true));
        this.addSequential(collectGroup);
        this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionGrabbing(),.6));
        this.addSequential(new SetClampOpen(false));
        if(isTeleop) {
            this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
        }
    }
}

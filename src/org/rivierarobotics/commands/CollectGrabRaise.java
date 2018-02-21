package org.rivierarobotics.commands;

import org.rivierarobotics.constants.RobotDependentConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectGrabRaise extends CommandGroup{
        
    public CollectGrabRaise() {
        CommandGroup lowerGroup = new CommandGroup();
        lowerGroup.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionCollectStandby()));
        lowerGroup.addParallel(new CollectSwitchStop());
        this.addSequential(new SetClampOpen(true));
        this.addSequential(lowerGroup);
        this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionGrabbing(),.6));
        this.addSequential(new SetClampOpen(false));
        this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
    }
    
}

package org.rivierarobotics.autos;
import org.rivierarobotics.autos.rightscale.WiggleWiggleWiggle;
import org.rivierarobotics.commands.CollectGrabRaise;
import org.rivierarobotics.commands.SetArmAngleGainScheduled;
import org.rivierarobotics.commands.SetClampOpen;
import org.rivierarobotics.constants.RobotDependentConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class DriveAndCollect extends CommandGroup{
    public DriveAndCollect() {
        this.addSequential(new SetClampOpen(true));
        this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
        this.addSequential(new TimedCommand(1));
        CommandGroup get = new CommandGroup();
        get.addParallel(new WiggleWiggleWiggle());
        get.addParallel(new CollectGrabRaise(false));
        this.addSequential(get);
    }
}

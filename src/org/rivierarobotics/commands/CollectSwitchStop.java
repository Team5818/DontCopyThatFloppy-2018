package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class CollectSwitchStop extends CommandGroup{
    public CollectSwitchStop() {
        this.addSequential(new CollectWithSwitch(Double.NaN,10.0));
        this.addSequential(new TimedCommand(0.5));
    }
}

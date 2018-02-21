package org.rivierarobotics.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CollectSwitchStop extends CommandGroup{
    public CollectSwitchStop() {
        this.addSequential(new CollectWithSwitch(.8,10.0));
        this.addSequential(new SetCollectorPower(.8, .5));
    }
}

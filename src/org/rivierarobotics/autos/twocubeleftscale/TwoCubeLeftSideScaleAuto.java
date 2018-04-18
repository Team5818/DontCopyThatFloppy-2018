package org.rivierarobotics.autos.twocubeleftscale;

import org.rivierarobotics.autos.BackAndForth;
import org.rivierarobotics.autos.SideDependentPunch;
import org.rivierarobotics.autos.SideDependentShift;
import org.rivierarobotics.autos.SideDependentWait;
import org.rivierarobotics.commands.CollectGrabRaise;
import org.rivierarobotics.commands.MagicSpin;
import org.rivierarobotics.commands.SetArmAngleGainScheduled;
import org.rivierarobotics.commands.SetClampOpen;
import org.rivierarobotics.commands.SetPuncher;
import org.rivierarobotics.commands.ShiftGear;
import org.rivierarobotics.constants.RobotDependentConstants;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class TwoCubeLeftSideScaleAuto extends CommandGroup {

    public TwoCubeLeftSideScaleAuto() {

        CommandGroup driveToScale = new CommandGroup();
        CommandGroup raiseGroup = new CommandGroup();
        driveToScale.addParallel(new DriveToScaleLeft2L());
        raiseGroup.addSequential(new TimedCommand(1));
        raiseGroup.addSequential(
                new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionScaleHigh() - 180));
        driveToScale.addParallel(raiseGroup);
        this.addSequential(driveToScale);
        this.addSequential(new SideDependentShift(Side.RIGHT, DriveGear.GEAR_LOW));
        this.addSequential(new RightSideOnlyTurnToCross2L());
        this.addSequential(new SideDependentShift(Side.RIGHT, DriveGear.GEAR_HIGH));
        this.addSequential(new RightSideOnlyDriveAcrossField2L());
        this.addSequential(new SideDependentShift(Side.RIGHT, DriveGear.GEAR_LOW));
        this.addSequential(new RightSideOnlySpinToScale2L());
        this.addSequential(new RightSideOnlyDriveToScale2L());
        this.addSequential(new SideDependentPunch(Side.LEFT));
        this.addSequential(new SetClampOpen(true));
        this.addSequential(new TimedCommand(.5));
        this.addSequential(new SetPuncher(false));
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
        this.addSequential(new TurnToCube2L());
        this.addSequential(new BackUpRight2L());
        this.addSequential(new SideDependentWait(Side.RIGHT, 15));
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));
        this.addSequential(new ScaleToCube2L());
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));

        CommandGroup collectGroup = new CommandGroup();
        collectGroup.addParallel(new BackAndForth());
        collectGroup.addParallel(new CollectGrabRaise(false));
        
        this.addSequential(collectGroup);
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));

         CommandGroup returnGroup = new CommandGroup();
         returnGroup.addParallel(new CubeToScale2L());
         returnGroup.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionScaleHigh() - 150));
         
         this.addSequential(returnGroup);
         
         this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
         this.addSequential(new MagicSpin(-45));
         this.addSequential(new SideDependentPunch(Side.LEFT));
         this.addSequential(new SetClampOpen(true));
         this.addSequential(new TimedCommand(.5));
         this.addSequential(new SetPuncher(false));
         this.addSequential(new BackUpLeft2L());
         this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
    }
}

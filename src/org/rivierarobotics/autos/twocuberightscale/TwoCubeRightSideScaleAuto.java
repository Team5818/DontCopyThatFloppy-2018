package org.rivierarobotics.autos.twocuberightscale;

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

public class TwoCubeRightSideScaleAuto extends CommandGroup {

    public TwoCubeRightSideScaleAuto() {

        CommandGroup driveToScale = new CommandGroup();
        CommandGroup raiseGroup = new CommandGroup();
        driveToScale.addParallel(new DriveToScaleRight2R());
        raiseGroup.addSequential(new TimedCommand(1));
        raiseGroup.addSequential(
                new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionScaleHigh() - 100));
        driveToScale.addParallel(raiseGroup);
        this.addSequential(driveToScale);
        this.addSequential(new SideDependentShift(Side.LEFT, DriveGear.GEAR_LOW));
        this.addSequential(new LeftSideOnlyTurnToCross2R());
        this.addSequential(new SideDependentShift(Side.LEFT, DriveGear.GEAR_HIGH));
        this.addSequential(new LeftSideOnlyDriveAcrossField2R());
        this.addSequential(new SideDependentShift(Side.LEFT, DriveGear.GEAR_LOW));
        this.addSequential(new LeftSideOnlySpinToScale2R());
        this.addSequential(new LeftSideOnlyDriveToScale2R());
        this.addSequential(new SideDependentPunch(Side.RIGHT));
        this.addSequential(new SetClampOpen(true));
        this.addSequential(new TimedCommand(.5));
        this.addSequential(new SetPuncher(false));
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
        this.addSequential(new BackUpLeft2R());
        this.addSequential(new TurnToCube2R());
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));
        this.addSequential(new ScaleToCube2R());
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));

        CommandGroup collectGroup = new CommandGroup();
        collectGroup.addParallel(new BackAndForth());
        collectGroup.addParallel(new CollectGrabRaise(false));
        
        this.addSequential(collectGroup);
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));

         CommandGroup returnGroup = new CommandGroup();
         returnGroup.addParallel(new CubeToScale2R());
         returnGroup.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionScaleHigh() - 180));
         
         this.addSequential(returnGroup);
         this.addSequential(new SideDependentWait(Side.LEFT, 15));
         this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
         this.addSequential(new MagicSpin(45));
         this.addSequential(new SideDependentPunch(Side.RIGHT));
         this.addSequential(new SetClampOpen(true));
         this.addSequential(new TimedCommand(.5));
         this.addSequential(new SetPuncher(false));
         this.addSequential(new BackUpRight2R());
         this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
    }
}

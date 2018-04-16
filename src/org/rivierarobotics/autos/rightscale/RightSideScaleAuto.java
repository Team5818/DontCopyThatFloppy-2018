package org.rivierarobotics.autos.rightscale;

import org.rivierarobotics.autos.BackAndForth;
import org.rivierarobotics.autos.SideDependentPunch;
import org.rivierarobotics.autos.SideDependentShift;
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

public class RightSideScaleAuto extends CommandGroup {

    public RightSideScaleAuto() {

        CommandGroup driveToScale = new CommandGroup();
        CommandGroup raiseGroup = new CommandGroup();
        driveToScale.addParallel(new DriveToScaleRight());
        raiseGroup.addSequential(new TimedCommand(1));
        raiseGroup.addSequential(
                new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionScaleHigh() - 180));
        driveToScale.addParallel(raiseGroup);
        this.addSequential(driveToScale);
        this.addSequential(new SideDependentShift(Side.LEFT, DriveGear.GEAR_LOW));
        this.addSequential(new LeftSideOnlyTurnToCross());
        this.addSequential(new SideDependentShift(Side.LEFT, DriveGear.GEAR_HIGH));
        this.addSequential(new LeftSideOnlyDriveAcrossField());
        this.addSequential(new SideDependentShift(Side.LEFT, DriveGear.GEAR_LOW));
        this.addSequential(new LeftSideOnlySpinToScale());
        this.addSequential(new LeftSideOnlyDriveToScale());
        this.addSequential(new SideDependentPunch(Side.RIGHT));
        this.addSequential(new SetClampOpen(true));
        this.addSequential(new TimedCommand(.5));
        this.addSequential(new SetPuncher(false));
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
        this.addSequential(new MagicSpin(120));
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));
        this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
        this.addSequential(new ScaleToCube());
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));

        CommandGroup collectGroup = new CommandGroup();
        collectGroup.addParallel(new BackAndForth());
        collectGroup.addParallel(new CollectGrabRaise(false));
        
        this.addSequential(collectGroup);
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));

         CommandGroup returnGroup = new CommandGroup();
         returnGroup.addParallel(new CubeToScaleTCS());
         returnGroup.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionScaleHigh() - 150));
         
         this.addSequential(returnGroup);
         
         this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
         this.addSequential(new MagicSpin(45));
         this.addSequential(new SideDependentPunch(Side.RIGHT));
         this.addSequential(new SetClampOpen(true));
         this.addSequential(new TimedCommand(.5));
         this.addSequential(new SetPuncher(false));
         this.addSequential(new BackUp());
         this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
    }
}

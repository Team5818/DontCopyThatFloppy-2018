package org.rivierarobotics.autos.baselinescale;

import org.rivierarobotics.autos.BackAndForth;
import org.rivierarobotics.autos.SideDependentPunch;
import org.rivierarobotics.autos.SideDependentShift;
import org.rivierarobotics.autos.SideDependentWait;
import org.rivierarobotics.commands.AutoPunch;
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

public class LeftSideBaselineScaleAuto extends CommandGroup {

    public LeftSideBaselineScaleAuto() {

        CommandGroup driveToScale = new CommandGroup();
        CommandGroup raiseGroup = new CommandGroup();
        driveToScale.addParallel(new DriveToScaleLeftBL());
        raiseGroup.addSequential(new TimedCommand(1));
        raiseGroup.addSequential(
                new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionScaleHigh() - 180));
        driveToScale.addParallel(raiseGroup);
        this.addSequential(driveToScale);
        this.addSequential(new SideDependentShift(Side.RIGHT, DriveGear.GEAR_LOW));
        this.addSequential(new RightSideOnlyTurnToCrossBL());
        this.addSequential(new SideDependentShift(Side.RIGHT, DriveGear.GEAR_HIGH));
        this.addSequential(new RightSideOnlyDriveAcrossFieldBL());
        this.addSequential(new SideDependentShift(Side.RIGHT, DriveGear.GEAR_LOW));
        this.addSequential(new RightSideOnlySpinToScaleBL());
        this.addSequential(new RightSideOnlyDriveToScaleBL());
        this.addSequential(new RightSideOnlyLastSpinBL());
        this.addSequential(new SideDependentPunch(Side.LEFT));
        this.addSequential(new AutoPunch(-.2));
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
        this.addSequential(new BackUpRightBL());
        this.addSequential(new SideDependentWait(Side.RIGHT, 15));
        this.addSequential(new TurnToCubeBL());
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));
        this.addSequential(new ScaleToCubeBL());
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));

        CommandGroup collectGroup = new CommandGroup();
        collectGroup.addParallel(new BackAndForth());
        collectGroup.addParallel(new CollectGrabRaise(false));
        
        this.addSequential(collectGroup);
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));

         CommandGroup returnGroup = new CommandGroup();
         returnGroup.addParallel(new CubeToScaleBL());
         returnGroup.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionScaleHigh() - 150));
         
         this.addSequential(returnGroup);
         this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
         this.addSequential(new MagicSpin(-45));
         this.addSequential(new SideDependentPunch(Side.LEFT));
         this.addSequential(new SetClampOpen(true));
         this.addSequential(new TimedCommand(.5));
         this.addSequential(new SetPuncher(false));
         this.addSequential(new BackUpLeftBL());
         this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
    }
}

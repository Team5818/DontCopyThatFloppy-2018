package org.rivierarobotics.autos.rightscale;

import org.rivierarobotics.autos.SideDependentPunch;
import org.rivierarobotics.commands.AutoPunch;
import org.rivierarobotics.commands.AutoThrow;
import org.rivierarobotics.commands.CollectGrabRaise;
import org.rivierarobotics.commands.ExecuteTrajectoryCommand;
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
import jaci.pathfinder.Waypoint;

public class TwoCubeScaleAuto extends CommandGroup {

    public TwoCubeScaleAuto() {

        CommandGroup driveToScale = new CommandGroup();
        CommandGroup raiseGroup = new CommandGroup();
        driveToScale.addParallel(new DriveToScaleRight());
        raiseGroup.addSequential(new TimedCommand(2));
        raiseGroup.addSequential(
                new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionScaleHigh() - 180));
        driveToScale.addParallel(raiseGroup);
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));
        this.addSequential(driveToScale);
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
        this.addSequential(new LeftSideOnlyTurnToCross());//both
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));
        this.addSequential(new LeftSideOnlyDriveAcrossField());//left
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
        this.addSequential(new LeftSideOnlySpinToScale());
        this.addSequential(new LeftSideOnlyDriveToScale());//both
        this.addSequential(new SideDependentPunch(Side.RIGHT));
        this.addSequential(new SetClampOpen(true));
        this.addSequential(new TimedCommand(.5));
        this.addSequential(new SetPuncher(false));
//        
//        this.addSequential(new ScaleToCube());
//        this.addSequential(new MagicSpin(180));
//
//        CommandGroup driveBackGroup = new CommandGroup();
//        CommandGroup getCubeGroup = new CommandGroup();
//        Waypoint[] driveToCube = new Waypoint[] { new Waypoint(0, 0, 0), new Waypoint(35, 0, 0) };
//        driveBackGroup.addSequential(new ExecuteTrajectoryCommand(driveToCube, false, -180));
//        driveBackGroup.addSequential(new WiggleWiggleWiggle());
//        
//        getCubeGroup.addParallel(driveBackGroup);
//        getCubeGroup.addParallel(new CollectGrabRaise(false));
//        this.addSequential(getCubeGroup);
//
//        CommandGroup placeGroup = new CommandGroup();
//        placeGroup.addParallel(new CubeToScaleTCS());
//        placeGroup.addParallel(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionBack()));
//        placeGroup.addParallel(new AutoThrow(RobotDependentConstants.Constant.getArmPositionScaleHigh() - 130));
//        this.addSequential(placeGroup);
//        this.addSequential(new TimedCommand(.5));
        this.addSequential(new BackUp());
        this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
    }
}

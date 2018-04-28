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

public class OneCubeScaleAutoRight extends CommandGroup {

    public OneCubeScaleAutoRight() {

        CommandGroup driveToScale = new CommandGroup();
        CommandGroup raiseGroup = new CommandGroup();
        driveToScale.addParallel(new DriveToScaleRight());
        raiseGroup.addSequential(new TimedCommand(1));
        raiseGroup.addSequential(
                new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionScaleHigh() - 180));
        driveToScale.addParallel(raiseGroup);
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));
        this.addSequential(driveToScale);
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
        this.addSequential(new LeftSideOnlyTurnToCrossR());//both
        this.addSequential(new ShiftGear(DriveGear.GEAR_HIGH));
        this.addSequential(new LeftSideOnlyDriveAcrossFieldR());//left
        this.addSequential(new ShiftGear(DriveGear.GEAR_LOW));
        this.addSequential(new LeftSideOnlySpinToScaleR());
        this.addSequential(new LeftSideOnlyDriveToScaleR());//both
        this.addSequential(new SideDependentPunch(Side.RIGHT));
        this.addSequential(new SetClampOpen(true));
        this.addSequential(new TimedCommand(.5));
        this.addSequential(new SetPuncher(false));
        this.addSequential(new BackUpR());
        this.addSequential(new SetArmAngleGainScheduled(RobotDependentConstants.Constant.getArmPositionSwitchMid()));
    }
}

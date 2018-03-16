/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.rivierarobotics.robot;

import org.rivierarobotics.autos.centerswitch.CenterSwitchAuto;
import org.rivierarobotics.autos.rightscale.TwoCubeScaleAuto;
import org.rivierarobotics.commands.CompressorControlCommand;
import org.rivierarobotics.commands.ExecuteTrajectoryCommand;
import org.rivierarobotics.constants.RobotMap;
import org.rivierarobotics.constants.Side;
import org.rivierarobotics.driverinterface.Driver;
import org.rivierarobotics.subsystems.Arm;
import org.rivierarobotics.subsystems.Clamp;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.subsystems.DriveTrain.DriveGear;
import org.rivierarobotics.subsystems.Floppies;
import org.rivierarobotics.util.CSVLogger;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Waypoint;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    private Command autonomousCommand;
    private Command switchInAuto;
    private Command switchOutAuto;
    private SendableChooser<Command> chooser = new SendableChooser<>();
    public DriveTrain driveTrain;
    public Driver driver;
    public Arm arm;
    public Floppies floppies;
    public Clamp clamp;
    public UsbCamera camCollect;
    public UsbCamera camBack;
    public VideoSink camServer;
    public CSVLogger logger;
    public Side[] fieldData;
    public PowerDistributionPanel pdp;
    public Compressor compressor;
    public DigitalInput autoSelector;
    private CompressorControlCommand compDisable;
    public static Robot runningRobot;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        runningRobot = this;
        driveTrain = new DriveTrain();
        arm = new Arm();
        clamp = new Clamp();
        floppies = new Floppies();
        pdp = new PowerDistributionPanel();
        compressor = new Compressor();
        driver = new Driver();
        autoSelector = new DigitalInput(RobotMap.AUTO_SELECTOR_SWITCH);

        String[] fields = { "Pos", "Vel", "Set Pos", "Set Vel", "Heading", "Set Heading", "Time" };
        logger = new CSVLogger("/home/lvuser/templogs/PROFILE_LOG", fields);

        switchInAuto = new CenterSwitchAuto();
        switchOutAuto = new TwoCubeScaleAuto();
        compDisable = new CompressorControlCommand(driver.JS_LEFT_BUTTONS);
    }

    public void queryFieldData() {
        String gameSide = DriverStation.getInstance().getGameSpecificMessage();
        Side[] side = new Side[3];
        if(gameSide.length() < 3) {
            side = new Side[] {Side.RIGHT,Side.RIGHT,Side.RIGHT};
            DriverStation.reportError("no game data", false);
        }
        else {
            for (int x = 0; x < 3; x++) {
                if (gameSide.charAt(x) == 'L')
                    side[x] = Side.LEFT;
                else
                    side[x] = Side.RIGHT;
            }
        }
        fieldData = new Side[] {Side.LEFT,Side.LEFT,Side.LEFT};;
    }

    public Side[] getSide() {
        return fieldData;
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     *
     * <p>
     * You can add additional auto modes by adding additional comparisons to the
     * switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        queryFieldData();
        driveTrain.resetGyro();
        driveTrain.shiftGear(DriveGear.GEAR_LOW);
        compressor.stop();
        if(autoSelector.get()) {
            autonomousCommand = switchOutAuto;
        }
        else{
            autonomousCommand = switchInAuto;
        }

        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        printDash();
    }

    @Override
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        compDisable.start();
        if (camCollect == null) {
            camCollect = CameraServer.getInstance().startAutomaticCapture(0);
            camCollect.setResolution(160, 120);
            camBack = CameraServer.getInstance().startAutomaticCapture(1);
            camBack.setResolution(160, 120);
            camServer = CameraServer.getInstance().getServer();
        }
        arm.stop();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        printDash();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
        printDash();
    }

    public void printDash() {
        SmartDashboard.putNumber("Arm Pos", arm.getPosition());
        SmartDashboard.putNumber("Left Roller", floppies.getLeftPos());
        SmartDashboard.putNumber("Right Roller", floppies.getRightPos());
        SmartDashboard.putNumber("Left Roller Trunc", floppies.getLeftTrunc());
        SmartDashboard.putNumber("Right Roller Trunc", floppies.getRightTrunc());
        SmartDashboard.putBoolean("Cube ready", floppies.cubeInPlace());
        SmartDashboard.putBoolean("Left Switch", floppies.leftSwitchAcitve());
        SmartDashboard.putBoolean("Right Switch", floppies.rightSwitchActive());
        SmartDashboard.putNumber("Left Inches", driveTrain.getDistanceInches().getX());
        SmartDashboard.putNumber("Right Inches", driveTrain.getDistanceInches().getY());
        SmartDashboard.putNumber("Avg Inches", driveTrain.getAvgSidePositionInches());
        SmartDashboard.putBoolean("Auto Chooser", autoSelector.get());
        SmartDashboard.putNumber("Yaw", driveTrain.getYaw());
    }
}

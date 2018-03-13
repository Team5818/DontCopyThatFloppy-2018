/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.rivierarobotics.robot;

import org.rivierarobotics.autos.rightscale.ScaleToCube;
import org.rivierarobotics.autos.rightscale.TwoCubeScaleAuto;
import org.rivierarobotics.autos.switchthenscale.DriveToSwitchFromRight;
import org.rivierarobotics.autos.switchthenscale.SwitchThenScaleAuto;
import org.rivierarobotics.autos.switchthenscale.SwitchToCube;
import org.rivierarobotics.commands.CompressorControlCommand;
import org.rivierarobotics.commands.ExecuteTrajectoryCommand;
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
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private String m_autoSelected;
    private SendableChooser<String> m_chooser = new SendableChooser<>();

    public DriveTrain driveTrain;
    public Driver driver;
    public Arm arm;
    public Floppies floppies;
    public Clamp clamp;
    public UsbCamera camCollect;
    public UsbCamera camBack;
    public VideoSink camServer;
    public CSVLogger logger;
    
    CommandGroup ex = new CommandGroup();

    public PowerDistributionPanel pdp;
    public Compressor compressor;

    public static Robot runningRobot;

    private CompressorControlCommand compDisable;

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

        String[] fields = { "Pos", "Vel", "Set Pos","Set Vel","Heading","Set Heading","Time"};
        logger = new CSVLogger("/home/lvuser/templogs/PROFILE_LOG", fields);
        
        m_chooser.addDefault("Default Auto", kDefaultAuto);
        m_chooser.addObject("My Auto", kCustomAuto);
        SmartDashboard.putData("Auto choices", m_chooser);
        compDisable = new CompressorControlCommand(driver.JS_LEFT_BUTTONS);

         ex = new TwoCubeScaleAuto();
    }

    public Side[] getSide() {
        String gameSide = DriverStation.getInstance().getGameSpecificMessage();

        Side[] side = new Side[3];

        for (int x = 0; x < 3; x++) {
            if (gameSide.charAt(x) == 'L')
                side[x] = Side.LEFT;
            else
                side[x] = Side.RIGHT;
        }

        return side;
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
        driveTrain.resetGyro();
        driveTrain.shiftGear(DriveGear.GEAR_LOW);
        compressor.stop();
        ex.start();
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
        compDisable.start();
        ex.cancel();
        if(camCollect == null) {
            camCollect = CameraServer.getInstance().startAutomaticCapture(0);
            camBack = CameraServer.getInstance().startAutomaticCapture(1);
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
        SmartDashboard.putNumber("Yaw", driveTrain.getYaw());
    }
}

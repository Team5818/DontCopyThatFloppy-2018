/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.rivierarobotics.robot;

import org.rivierarobotics.driverinterface.Driver;
import org.rivierarobotics.mathUtil.CSVLogger;
import org.rivierarobotics.subsystems.Arm;
import org.rivierarobotics.subsystems.Clamp;
import org.rivierarobotics.subsystems.DriveTrain;
import org.rivierarobotics.subsystems.Floppies;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    String[] fields = {"Position","Velocity","Power"};
	public CSVLogger logger = new CSVLogger("/media/sdc1/FRCDrive/ARM_LOG.csv",fields);
	public Clamp clamp;

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
	    driver = new Driver();
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// m_autoSelected = SmartDashboard.getString("Auto Selector",
		// 		kDefaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	@Override 
	public void teleopInit() {
	       arm.stop();
	}
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		csvLog();
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
	    SmartDashboard.putNumber("Left Roller", floppies.getLeftPos());
	    SmartDashboard.putNumber("Right Roller", floppies.getRightPos());
	    SmartDashboard.putNumber("Left Roller Trunc", floppies.getLeftTrunc());
	    SmartDashboard.putNumber("Right Roller Trunc", floppies.getRightTrunc());
	    SmartDashboard.putNumber("Arm Position", arm.getPosition());
	}
	
	public void csvLog() {
        double[] armVals = {arm.getPosition(), arm.getVelocity(),arm.getPower()};
        logger.writeImmediately(armVals);
	}
}

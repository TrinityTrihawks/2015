package org.usfirst.frc.team4215.robot;


import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot { 
	
	// Talon def
	Talon frontLeft = new Talon(0);
	Talon backLeft = new Talon(1);
	Talon backRight = new Talon(2);			
	Talon frontRight = new Talon(3);
	
	Ultrasonic rangefinder = new Ultrasonic(1,2);
	
	Timer time = new Timer();
	
	double frontLeftDriveValue = 0.0;
	double frontRightDriveValue = 0.0;
	double backLeftDriveValue = 0.0;
	double backRightDriveValue = 0.0;
		
	
	private final double maxInputDriver = 1.0;
    private final double minInputDriver = 0.0;
		
    public void operatorControl() {   	
        while (isOperatorControl() && isEnabled()) {
        }
    }
        
    public void autonomous() {

    	boolean autoComplete = false;
        while (isAutonomous() && isEnabled() && !autoComplete) {	
        	try {
        		autonomousD();
        	}
        	catch(Exception e) {
        		SmartDashboard.putString("Exception", e.getMessage());
        	}
        	finally {
        		autoComplete = true;
        	}
        }
    }

    public void autonomousA() {
//    	RobotDrive myRobotDrive = new RobotDrive(0, 1, 2, 3);
//    	Gyro gyro = new Gyro(0);
//    	gyro.initGyro();
    	//TODO: gyro sampling rate
    	    	
    	// move foward to tote
    	while(rangefinder.getRangeMM() < 500) {
//    		myRobotDrive.mecanumDrive_Cartesian(.25, 0, 0, gyro.getAngle());
    		SmartDashboard.putNumber("rangeFinder dist", rangefinder.getRangeMM());
    		SmartDashboard.putData("rangeFinder units", (Sendable)rangefinder.getDistanceUnits());
    	}
//    	myRobotDrive.stopMotor();
		frontLeft.set(0);
		backLeft.set(0);
		backRight.set(0);			
		frontRight.set(0);
    }

    public void autonomousB() {
    	double range = 0.0;
    	double value = 0.0;
        do {
        	value = rangefinder.pidGet();
            range = (value / (5.0 / 512.0));                // The 5 is the voltage range, 512 is
    		SmartDashboard.putNumber("rangeFinder value", value);
    		SmartDashboard.putNumber("rangeFinder range", range);
        }
    	while(range < 500);
    }
        
    public void autonomousC() {
//    	RobotDrive myRobotDrive = new RobotDrive(0, 1, 2, 3);
    	Gyro gyro = new Gyro(0);
    	gyro.initGyro();
    	    	
    	while(gyro.getAngle() < 360) {
//    		myRobotDrive.mecanumDrive_Cartesian(.25, 0, 0, gyro.getAngle());
    		frontLeft.set(.3);
    		backLeft.set(.3);
    		backRight.set(.3);			
    		frontRight.set(.3);
    		SmartDashboard.putNumber("gyro degrees", gyro.getAngle());
    		SmartDashboard.putNumber("gyro rate", gyro.getRate());
    	}
//    	myRobotDrive.stopMotor();
		frontLeft.set(0);
		backLeft.set(0);
		backRight.set(0);			
		frontRight.set(0);
    }

    public void autonomousD() {
    	
    	I2CGyro gyro = new I2CGyro();
    	
    	double timerStart = Timer.getFPGATimestamp();
    	double timerNow = Timer.getFPGATimestamp();
    	double degrees = 0.0;
    	{
    		frontLeft.set(.4);
    		backLeft.set(.4);
    		backRight.set(.4);			
    		frontRight.set(.4);
    		
    		timerNow = Timer.getFPGATimestamp();
    		degrees = gyro.getDegrees(timerStart - timerNow);
    		SmartDashboard.putNumber("gyro degrees", degrees);
    	} while (degrees < 360 && isAutonomous() && isEnabled());
    	
		frontLeft.set(0);
		backLeft.set(0);
		backRight.set(0);			
		frontRight.set(0);
    }

    /** 
     * Runs during test mode
     */
    public void test() {    	
    }
}

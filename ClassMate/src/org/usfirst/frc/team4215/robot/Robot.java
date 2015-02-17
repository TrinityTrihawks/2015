package org.usfirst.frc.team4215.robot;


import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
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
	
	   
	// Objects defined for drive train.
	Joystick leftStick = new Joystick(0);
	Joystick rightStick = new Joystick(1);
	Joystick thirdStick = new Joystick(2);
	
	// Talon def
	Talon frontLeft = new Talon(0);
	Talon backLeft = new Talon(1);
	Talon backRight = new Talon(2);			
	Talon frontRight = new Talon(3);
	Talon elevator = new Talon(4);
	Talon rackPinion = new Talon(6);
	Victor brake = new Victor(7);
	
	Ultrasonic rangefinder = new Ultrasonic(2,1);
	
	BuiltInAccelerometer accg = new BuiltInAccelerometer();
	Timer time = new Timer();

	int count = 0;
	
	double accmss;
	double speedms;
	double hertz;
	
	final double autogo = 1.168;
	final long wt = 100; 
	
	
	double frontLeftDriveValue = 0.0;
	double frontRightDriveValue = 0.0;
	double backLeftDriveValue = 0.0;
	double backRightDriveValue = 0.0;
	double elevatorDriveValue = 0.5;
	
	
	PowerDistributionPanel pdp = new PowerDistributionPanel();
//	AnalogInput test = new AnalogInput(0);
	
	double elevation;
	double arms;
	
	boolean brakeEngaged;
	int brakeEngagedCount = 0;
	
		
    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
    	
    	frontLeft.setSafetyEnabled(false);
    	frontRight.setSafetyEnabled(false);
    	backRight.setSafetyEnabled(false);
    	backLeft.setSafetyEnabled(false);
    	elevator.setSafetyEnabled(false);
    	rackPinion.setSafetyEnabled(false);
    	brake.setSafetyEnabled(false);
    	
   	    // driveForward();
       	binSet();
        //backupPlan();
            
    }
    
    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        
    	frontLeft.setSafetyEnabled(true);
    	frontRight.setSafetyEnabled(true);
    	backRight.setSafetyEnabled(true);
    	backLeft.setSafetyEnabled(true);
    	elevator.setSafetyEnabled(true);
    	rackPinion.setSafetyEnabled(true);
    	brake.setSafetyEnabled(true);
    	
    	
    	brakeEngaged = true;
    	
        while (isOperatorControl() && isEnabled()) {
            drivingMethod();
            Elevator();
            rackMethod();
            brakeMethod();
            SmartDashboard.putNumber("Current 1",pdp.getCurrent(0));
            SmartDashboard.putNumber("Current 2",pdp.getCurrent(1));
            SmartDashboard.putNumber("Current 3",pdp.getCurrent(2));
            SmartDashboard.putNumber("Current 4",pdp.getCurrent(3));
        	SmartDashboard.putNumber("Front Left Value",frontLeftDriveValue);
        	SmartDashboard.putNumber("Front Right Value",frontRightDriveValue);
        	SmartDashboard.putNumber("Back Left Value",backLeftDriveValue);
        	SmartDashboard.putNumber("Back Right Value",backRightDriveValue);
        	SmartDashboard.putNumber("Elevator",elevatorDriveValue);
        	SmartDashboard.putNumber("Accleration M/s^2", accmss);
        	SmartDashboard.putNumber("Speed M/s", speedms);
//            SmartDashboard.putData("Single Point",test);
        }
    }
    
    public void drivingMethod(){
    	
    	double tank = leftStick.getY();
    	double tank2 = rightStick.getY();
    	double strafe = rightStick.getX();
    	
    	accmss = accg.getX()*9.81;
    	speedms = accmss * .001;
    	
    	frontLeft.set(-tank + strafe);
    	backLeft.set(-tank - strafe);
    	backRight.set(tank2 - strafe);
    	frontRight.set(tank2 + strafe);
    	
    	
    }

    public void Elevator() {
    	
    	double elevation;

    	elevation = thirdStick.getRawAxis(2);
    	    	    	    	
    	elevatorDriveValue = elevation;
    	elevator.set(elevation);  
    	
    	}	
           
    public void brakeMethod(){
    	 
		if(thirdStick.getRawButton(5)) {
			if (brakeEngagedCount >= 10 ) {
				brake.setSafetyEnabled(false);
				if (brakeEngaged == true) {
		    		brake.set(.5);
		    		Timer.delay(.1);
		    		brakeEngaged = false;
				}
				else {
			    		brake.set(-.75);
			    		Timer.delay(.1);
			    		brakeEngaged = true;
				}
				//Timer.delay(.1);
				brake.set(.0);
				brake.setSafetyEnabled(true);
				brakeEngagedCount = 0;
			} else {
				brakeEngagedCount++;
			}
    	}		
    }

    public void rackMethod(){ 	// Lauren&Margaret&Emma wrote this part
   	
    	arms = thirdStick.getX();  	   	
    	rackPinion.set(arms * .5);   	
    }
    
    public void binSet(){
    	// lift bin
    	elevator.set(-1);
    	Timer.delay(.5);
    	elevator.set(0);
    	
    	// drive backward
    	backLeft.set(-.65);
    	backRight.set(.65);
    	frontLeft.set(-.65);
    	frontRight.set(.65);
    	Timer.delay(.75);
    	backLeft.set(0);
    	backRight.set(0);
    	frontLeft.set(0);
    	frontRight.set(0);
    	
    	// lower elevator
    	elevator.set(1);
    	Timer.delay(.2);
    	elevator.set(0);
    	
    	// open claw
    	rackPinion.set(-.35);
    	Timer.delay(.25);
    	rackPinion.set(0);
    	
    	// raise elevator
    	elevator.set(-1);
    	Timer.delay(.50);
    	elevator.set(0);
    	
    	// strafe left
    	backLeft.set(.65);
    	backRight.set(.65);
    	frontRight.set(-.75);
    	frontLeft.set(-.75);
    	Timer.delay(1);
    	backLeft.set(0);
    	backRight.set(0);
    	frontRight.set(0);
    	frontLeft.set(0);
    	
    	// lower elevator
    	elevator.set(1);
    	Timer.delay(.5);
    	elevator.set(0);
    	
    	// drive forward
    	backLeft.set(1);
    	backRight.set(-1);
    	frontRight.set(-1);
    	frontLeft.set(1);
    	Timer.delay(1.5);
    	backLeft.set(0);
    	backRight.set(0);
    	frontRight.set(0);
    	frontLeft.set(0);
    	
    	// shut claw
    	rackPinion.set(.35);
    	Timer.delay(.25);
    	rackPinion.set(0);
    	
    	// raise elevator
    	
    	
    	
    	
    	
    	
    }
    
   
    public void driveForward(){
    	frontLeft.set(.75);
    	backLeft.set(.75);
    	frontRight.set(-.75);
    	backRight.set(-.75);
    	Timer.delay(.9);
    	frontLeft.set(0);
    	backLeft.set(0);
    	frontRight.set(0);
    	backRight.set(0);
    }
    
    /** 
     * Runs during test mode
     */
    public void test() {
    	
    	}
	
    }


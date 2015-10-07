package org.usfirst.frc.team4215.robot;


import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
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
	public static void main(String[] args) {
		
	}
	   
//	// Objects defined for drive train.
//	Joystick leftStick = new Joystick(0);
//	Joystick rightStick = new Joystick(1);
//	Joystick thirdStick = new Joystick(2);
//		
//	// Talon definition.
//	Talon frontLeft = new Talon(0);
//	Talon backLeft = new Talon(1);
//	Talon backRight = new Talon(2);
//	Talon frontRight = new Talon(3);
//
//	Talon elevator1 = new Talon(4);
//	Talon elevator2 = new Talon(5);
//	Talon rackPinion = new Talon(6);
//	
//	DigitalInput outerLimitSwitch = new DigitalInput(1);
//	DigitalInput innerLimitSwitch = new DigitalInput(2);
//	DigitalInput upperElevatorLimitSwitch = new DigitalInput(3);
//	DigitalInput lowerElevatorLimitSwitch = new DigitalInput(4);
//	
//	SinglePoint leftSensor = new SinglePoint(0 , 0 , 1);
//	SinglePoint rightSensor = new SinglePoint(1 , 0 , 1);
//
//	Victor brake = new Victor(7);
//		
//	private final double maxInputDriver = .75;
//    private final double minInputDriver = .15;
//	private final double maxInputElevation = 0.9;
//	private final double minInputElevation = 0.1;
//	private final double maxInputRack = 0.9;
//	private final double minInputRack = 0.1;
//	private final double cautionInput=-.05;
	
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
	
	Timer time = new Timer();
		
	int count = 0;
	
	
	double frontLeftDriveValue = 0.0;
	double frontRightDriveValue = 0.0;
	double backLeftDriveValue = 0.0;
	double backRightDriveValue = 0.0;
	double elevatorDriveValue = 0.0;
	double rackValue = 0.0;
	
	double RACKINPUTSCALE = .75;
	
	
//	PowerDistributionPanel pdp = new PowerDistributionPanel();
//	AnalogInput test = new AnalogInput(0);
	
	double elevation;
	double arms;
	final double autogo = 116.8;
	final long wt = 100; 
	
	private final double maxInputDriver = .75;
    private final double minInputDriver = .15;
	private final double maxInputElevation = 0.9;
	private final double minInputElevation = 0.1;
	private final double maxInputRack = 0.9;
	private final double minInputRack = 0.1;
	private final double cautionInput=-.05;

	boolean brakeEngaged;
	int brakeEngagedCount = 0;
	
    /**
     * Drive left & right motors for 2 seconds then stop
     */
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	AnalogInput test = new AnalogInput(0);
  
	public void autonomous() {
    	
	
		AutonomousBinToteLift();
		
		
		boolean autoComplete = false;
        while (isAutonomous() && isEnabled()) {
    		//autonomousTimeBased();
        	//binSet();

/*        	try {
//        		autonomousA();
        	}
        	catch(Exception e) {
        		SmartDashboard.putString("Exception", e.getMessage());
        	}
        	finally {
        		autoComplete = true;
        	}
*/        }
    }
    
    public void operatorControl(){
        
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
        	SmartDashboard.putNumber("Rack",rackValue);
        }
    }
    
    public void drivingMethod(){
    	
    	double tank = leftStick.getY();
    	double tank2 = rightStick.getY();
    	double strafe = rightStick.getX();
    	
    	frontLeft.set(-tank + strafe);
    	backLeft.set(-tank - strafe);
    	backRight.set(tank2 - strafe);
    	frontRight.set(tank2 + strafe);
    	    }

    public void Elevator() {
    	
    	Boolean elevation;

    	if (thirdStick.getRawButton(1) && !(thirdStick.getRawButton(3))){
    		elevator.set(-.5);
    	}
    	else if(!(thirdStick.getRawButton(1)) && (thirdStick.getRawButton(3))){
    		elevator.set(.5);
    	}
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

    public void rackMethod() { 	// Lauren&Margaret&Emma wrote this part
   	
		/*
		float axis = stick.getRawAxis(axisNumber); //see table below for axis numbers
		boolean button = stick.getRawButton(buttonNumber);
		
		Axis indexes:
			1 - LeftX
			2 - LeftY
			3 - Triggers (Each trigger = 0 to 1, axis value = right - left)
			4 - RightX
			5 - RightY
			6 - DPad Left/Right
			(http://www.chiefdelphi.com/forums/showthread.php?threadid=82825)
    	*/

    	arms = thirdStick.getX();  	
    	rackValue = arms;
    	rackPinion.set(rackValue);   
    	
    }

    private double joystickInputConditioning(double input, final double cautionInput, double minInput, double maxInput) {

		if (Math.abs(input) >= maxInput) {
    		if (input >= maxInput){
    			input = maxInput;
    		}
    		else
    		{
    			input = -maxInput;
    		}
    	}
    	else if (Math.abs(input) < minInput) {
    		if (input >= cautionInput){
    			input = minInput;
    		}
    		else if (input <= -cautionInput){
    			input = -minInput;
    		}
    		else
    		{
    			input = 0;
    		}
    	}
		return input;
	}    
    

    /**
     * Runs during test mode
     */    
//    public void autonomousA() {
//    	RobotDrive myRobotDrive = new RobotDrive(0, 1, 2, 3);
//    	Gyro gyro = new Gyro(0);
//    	gyro.initGyro();
//    	//TODO: gyro sampling rate
//    	
//    	// minimum input for rack to prevent the bin from slipping
//    	rackPinion.set(-.02);
//    	
//    	// lift the bin
//    	elevator1.set(.5);
//    	Timer.delay(.5);
//    	
//    	// move foward to tote
//    	while(ultrasonic.getRangeMM() < 500) {
//    		myRobotDrive.mecanumDrive_Cartesian(.25, 0, 0, gyro.getAngle());
//    	}
//    	myRobotDrive.stopMotor();
//    	
//    	// lower bin onto the tote
//    	elevator1.set(-.25);
//    	Timer.delay(.8);
//    	
//    	// open arms
//    	rackPinion.set(.25);
//    	Timer.delay(.3);
//    	rackPinion.stopMotor();
//    	
//    	// move arms down to the tote level
//    	elevator1.set(-.5);
//    	Timer.delay(.2);
//    	elevator1.stopMotor();
//    	
//    	// close arms
//    	rackPinion.set(.3);
//    	Timer.delay(.25);
//    	rackPinion.set(-.02);
//    	
//    	// lift the tote
//    	elevator1.set(.4);
//    	Timer.delay(.1);
//    	elevator1.stopMotor();
//    	
//    	// rotate the robot
//    	while (gyro.getAngle() < 90) {
//    		myRobotDrive.mecanumDrive_Cartesian(0, 0, .25, gyro.getAngle());
//    	}
//    	myRobotDrive.stopMotor();
//    	
//    	// drive the robot forward into the auto zone
//    	while(ultrasonic.getRangeMM() < 500) {
//    		myRobotDrive.mecanumDrive_Cartesian(.25, 0, 0, gyro.getAngle());
//    	}
//    	myRobotDrive.stopMotor();    	
//    }

    
    public void autonomousTimeBased() {
//    	RobotDrive myRobotDrive = new RobotDrive(0, 1, 2, 3);
    	
    	frontLeft.setSafetyEnabled(false);
    	frontRight.setSafetyEnabled(false);
    	backLeft.setSafetyEnabled(false);
    	backRight.setSafetyEnabled(false);
    	elevator.setSafetyEnabled(false);
    	rackPinion.setSafetyEnabled(false);
  /*  	
    	// minimum input for rack to prevent the bin from slipping
    	rackPinion.set(-.02);
    	
    	// lift the bin
    	elevator.set(-1);
    	Timer.delay(1.25);
    	elevator.set(0);
    	
    	// move foward to tote
    	frontLeft.set(.25);
    	backLeft.set(.25);
    	backRight.set(-.25);
    	frontRight.set(-.25);
    	Timer.delay(.75);
    	
    	frontLeft.set(0);
    	backLeft.set(0);
    	backRight.set(0);
    	frontRight.set(0);
    */	
    	// open arms
    	rackPinion.set(-.5);
    	Timer.delay(.3);
    	rackPinion.set(0);
    	/*	
    	// lower bin onto the tote
    	elevator.set(.5);
    	Timer.delay(.5);
    	elevator.set(0);
    	
    	// move arms down to the tote level
    	elevator.set(1);
    	Timer.delay(.5);
    	elevator.stopMotor();
    	
    	// close arms
    	rackPinion.set(-.3);
    	Timer.delay(.25);
    	rackPinion.set(-.02);
    	
    	// lift the tote
    	elevator.set(-.4);
    	Timer.delay(.75);
    	elevator.stopMotor();
    	*/
    	// rotate the robot
/*    	frontLeft.set(-1);
    	backLeft.set(-1);
    	backRight.set(-1);
    	frontRight.set(-1);
    	Timer.delay(.25);
    	
    	// drive the robot forward into the auto zone
    	frontLeft.set(.5);
    	backLeft.set(.5);
    	backRight.set(-.5);
    	frontRight.set(-.5);
    	Timer.delay(2);
    	
    	frontLeft.set(.25);
    	backLeft.set(.25);
    	backRight.set(-.25);
    	frontRight.set(-.25);
    	Timer.delay(.5);
    	
    	frontLeft.set(0);
    	backLeft.set(0);
    	backRight.set(0);
    	frontRight.set(0);
*/    }

    
    public void binSet(){
    	
    	frontLeft.setSafetyEnabled(false);
    	frontRight.setSafetyEnabled(false);
    	backLeft.setSafetyEnabled(false);
    	backRight.setSafetyEnabled(false);
    	elevator.setSafetyEnabled(false);
    	rackPinion.setSafetyEnabled(false);
    	
   /* 	
    	// drive forward 
    	frontLeft.set(.75);
    	frontRight.set(-.75);
    	backRight.set(-.75);
    	backLeft.set(.75);
    	Timer.delay(1.25);
    	frontLeft.set(.25);
    	frontRight.set(-.25);
    	backRight.set(-.25);
    	backLeft.set(.25);
    	Timer.delay(.25);
    	frontLeft.set(0);
    	backLeft.set(0);
    	backRight.set(0);
    	frontRight.set(0);
    */
    /*	
    	// turn
    	frontRight.set(.75);
    	frontLeft.set(.75);
    	backLeft.set(.75);
    	backRight.set(.75);
    	Timer.delay(.45);
    	frontLeft.set(0);
    	backLeft.set(0);
    	backRight.set(0);
    	frontRight.set(0);
    */
    /*	
    	// drive forward 
    	frontLeft.set(.75);
    	frontRight.set(-.9);
    	backRight.set(-.9);
    	backLeft.set(.75);
    	Timer.delay(.65);
    	frontLeft.set(0);
    	backLeft.set(0);
    	backRight.set(0);
    	frontRight.set(0);
    */
    /*	// turn
    	frontRight.set(.75);
    	frontLeft.set(.75);
    	backLeft.set(.75);
    	backRight.set(.75);
    	Timer.delay(.5);
    	frontLeft.set(0);
    	backLeft.set(0);
    	backRight.set(0);
    	frontRight.set(0);
    */
    /*	
    	// drive forward 
    	frontLeft.set(.75);
    	frontRight.set(-1);
    	backRight.set(-1);
    	backLeft.set(.75);
    	Timer.delay(1);
    	frontLeft.set(0);
    	backLeft.set(0);
    	backRight.set(0);
    	frontRight.set(0);
    */
    	frontLeft.set(.45);
    	frontRight.set(-.5);
    	backRight.set(-.5);
    	backLeft.set(.45);
    	Timer.delay(1.75);
    	frontLeft.set(0);
    	backLeft.set(0);
    	backRight.set(0);
    	frontRight.set(0);
    	
    }
    
    public void AutonomousSetSafetyAllMotors(boolean flag)
    {
    	frontLeft.setSafetyEnabled(flag);
    	frontRight.setSafetyEnabled(flag);
    	backLeft.setSafetyEnabled(flag);
    	backRight.setSafetyEnabled(flag);
    	elevator.setSafetyEnabled(flag);
    	rackPinion.setSafetyEnabled(flag);    	
    }
    
    public void AutonomousStopDriveTrain() {
		frontLeft.stopMotor();
    	backLeft.stopMotor();
    	backRight.stopMotor();
    	frontRight.stopMotor();   	
    }
    
    public void AutonomousBinToteLift() {
    	AnalogUltrasonic rangefinder = new AnalogUltrasonic(2);
    	
    	AutonomousSetSafetyAllMotors(false);
    	
    	// lift the bin
    	elevator.set(-.75);
    	Timer.delay(1.25);
    	elevator.stopMotor();
    	
    	double delta = 0;

    	
    	SmartDashboard.putNumber("rangeFinder dist", 0);
		SmartDashboard.putNumber("rangeFinder voltage", 0);
		
    	// move the robot forward
    	while(rangefinder.GetRangeInCM() < (delta + 3.6)) {
    		SmartDashboard.putNumber("rangeFinder dist", rangefinder.GetRangeInCM());
    		SmartDashboard.putNumber("rangeFinder voltage", rangefinder.GetVoltage());
    		
    		frontLeft.set(.25);
        	backLeft.set(.25);
        	backRight.set(-.25);
        	frontRight.set(-.25);
    	}
    	
    	AutonomousStopDriveTrain();
    	   	
    	// open arms
    	rackPinion.set(-1);
    	Timer.delay(.5);
    	rackPinion.set(0);
    	
    	// move arms down to the tote level
    	elevator.set(.8);
    	Timer.delay(1.6);
    	elevator.stopMotor();
    	
    	// close arms
    	rackPinion.set(.8);
    	Timer.delay(1.3);
    	
    	// lift tote
    	elevator.set(-.8);
    	Timer.delay(.5);
    	elevator.stopMotor();
//    	
//    	// rotate robot
//    	frontLeft.set(-.25);
//    	backLeft.set(-.25);
//    	backRight.set(-.25);
//    	frontRight.set(-.25);
//    	Timer.delay(1);
//    	
//    	frontLeft.set(0);
//    	backLeft.set(0);
//    	backRight.set(0);
//    	frontRight.set(0);
//    	
//    	// move into the auto zone
//    	while(rangefinder.GetRangeInCM() < (delta + 400)) {
//    		frontLeft.set(.25);
//        	backLeft.set(.25);
//        	backRight.set(-.25);
//        	frontRight.set(-.25);
//    	}
//    	AutonomousStopDriveTrain();
    	
    	AutonomousSetSafetyAllMotors(true);
    }
    
    public void autonomousA() {
    	
    	AnalogUltrasonic rangefinder = new AnalogUltrasonic(2);
//    	RobotDrive myRobotDrive = new RobotDrive(0, 1, 2, 3);
//    	Gyro gyro = new Gyro(0);
//    	gyro.initGyro();
    	//TODO: gyro sampling rate
    	    	
    	// move foward to tote
    	while(rangefinder.GetRangeInCM() < 50) {
//    		frontLeft.set(.2);
//    		backLeft.set(.2);
//    		backRight.set(-.2);
//    		frontRight.set(-.2);
    		SmartDashboard.putNumber("rangeFinder dist", rangefinder.GetRangeInCM());
    		SmartDashboard.putNumber("rangeFinder voltage", rangefinder.GetVoltage());
    	}
//    	myRobotDrive.stopMotor();
    	frontLeft.stopMotor();
		backLeft.stopMotor();
		backRight.stopMotor();
		frontRight.stopMotor();
    }

    
    public void test() {
    }
}

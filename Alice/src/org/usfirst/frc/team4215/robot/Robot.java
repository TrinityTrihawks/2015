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
	double elevatorDriveValue = 0.0;
	
	double RACKINPUTSCALE = .75;
	
	
//	PowerDistributionPanel pdp = new PowerDistributionPanel();
//	AnalogInput test = new AnalogInput(0);
	
	double elevation;
	double arms;
	
	boolean brakeEngaged;
	int brakeEngagedCount = 0;
	

    /**
     * Drive left & right motors for 2 seconds then stop
     */
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	AnalogInput test = new AnalogInput(0);
    public void autonomous() {
        while (!isOperatorControl() && isEnabled()) {
//        	autoStrafe();
        	SmartDashboard.putNumber("Current 1",pdp.getCurrent(0));
        	SmartDashboard.putNumber("Current 2",pdp.getCurrent(1));
        	SmartDashboard.putNumber("Current 3",pdp.getCurrent(2));
        	SmartDashboard.putNumber("Current 4",pdp.getCurrent(3));
        	SmartDashboard.putData("Single Point",test);
        }
        	
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
    	rackPinion.set(arms * RACKINPUTSCALE);   	
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
    
    public void rackMethodAlt(){ 	// Lauren&Margaret&Emma wrote this part
    	
//    	double arms = thirdStick.getRawAxis(4);
//    	
//		/*
//		float axis = stick.getRawAxis(axisNumber); //see table below for axis numbers
//		boolean button = stick.getRawButton(buttonNumber);
//		
//		Axis indexes:
//			1 - LeftX
//			2 - LeftY
//			3 - Triggers (Each trigger = 0 to 1, axis value = right - left)
//			4 - RightX
//			5 - RightY
//			6 - DPad Left/Right
//			(http://www.chiefdelphi.com/forums/showthread.php?threadid=82825)
//    	*/
//   
//    	arms = joystickInputConditioning(arms, cautionInput, minInputRack, maxInputRack);
//    	
//    	if (outerLimitSwitch.get() && arms > 0){
//    		arms = 0;
//    	}
//    	else if (innerLimitSwitch.get() && arms < 0){
//    		arms = 0;
//    	}
//    	
//    	rackPinion.set(arms);    	
    }


//    public void autonomousGripTote() {    //TDJ wrote this
//    	
//    	while (!innerLimitSwitch.get()) {
//        	rackPinion.set(.75);   		
//    	}
//    	rackPinion.set(0);
//
//    }
//
//    public void autoStrafe(){
//    	
//    	int counter = 0;
//    	
//    	frontLeft.setSafetyEnabled(false);
//    	frontRight.setSafetyEnabled(false);
//    	backLeft.setSafetyEnabled(false);
//    	backRight.setSafetyEnabled(false);
//    	
//        while (counter < 4) {
//        	frontRight.set(.5);
//        	backLeft.set(.5);
//        	frontLeft.set(-.5);
//        	backRight.set(-.5);
//        	
//        	leftSensor.scan();
//        	rightSensor.scan();
//        	
//        	if ( (leftSensor.scan() <= 50) && (leftSensor.scan() > 0) ) {
//        		counter = counter + 1;
//        	}
//        	else {
//        		
//        	}
//        	
//        	// if count is 3, slow down to half speed
//        	if (counter == 3) {
//        		frontRight.set(.25);
//            	backLeft.set(.25);
//            	frontLeft.set(-.25);
//            	backRight.set(-.25);
//        	}
//        }
//        
//        
//        frontRight.set(0);
//    	backLeft.set(0);
//    	frontLeft.set(0);
//    	backRight.set(0);
//    	
//    	counter = 0;
//    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}

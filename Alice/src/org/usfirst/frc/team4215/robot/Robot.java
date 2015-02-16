package org.usfirst.frc.team4215.robot;


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
	   
	// Objects defined for drive train.
	Joystick leftStick = new Joystick(0);
	Joystick rightStick = new Joystick(1);
	Joystick thirdStick = new Joystick(2);
		
	// Talon definition.
	Talon frontLeft = new Talon(0);
	Talon backLeft = new Talon(1);
	Talon backRight = new Talon(2);
	Talon frontRight = new Talon(3);

	Talon elevator1 = new Talon(4);
	Talon elevator2 = new Talon(5);
	Talon rackPinion = new Talon(6);
	
	DigitalInput outerLimitSwitch = new DigitalInput(1);
	DigitalInput innerLimitSwitch = new DigitalInput(2);
	DigitalInput upperElevatorLimitSwitch = new DigitalInput(3);
	DigitalInput lowerElevatorLimitSwitch = new DigitalInput(4);
	
	BuiltInAccelerometer accg = new BuiltInAccelerometer();
	
	Timer time = new Timer();
	
	SinglePoint leftSensor = new SinglePoint(0 , 0 , 1);
	SinglePoint rightSensor = new SinglePoint(1 , 0 , 1);
	
	Victor brake = new Victor(7);
	
	Ultrasonic ultrasonic = new Ultrasonic(0 , 1);		//TODO: ports?
	
	int count = 0;
	
	double accmss;
	double speedms;
	double hertz;
	
	final double autogo = 116.8;
	final long wt = 100; 
	
	private final double maxInputDriver = .75;
    private final double minInputDriver = .15;
	private final double maxInputElevation = 0.9;
	private final double minInputElevation = 0.1;
	private final double maxInputRack = 0.9;
	private final double minInputRack = 0.1;
	private final double cautionInput=-.05;
	
	

    public void autonomous() {
    	boolean autoComplete = false;
        while (isAutonomous() && isEnabled() && !autoComplete) {
        	
        	try {
        		autonomousA();
        	}
        	catch(Exception e) {
        		SmartDashboard.putString("Exception", e.getMessage());
        	}
        	finally {
        		autoComplete = true;
        	}
        }
    }
    
    public void operatorControl(){
        
        while (isOperatorControl() && isEnabled()) {
        	SmartDashboard.putNumber("Accleration M/s^2", accmss);
        	SmartDashboard.putNumber("Speed M/s", speedms);
            drivingMethod();
            try {
				time.wait(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    public void drivingMethod(){
    	
    	double tankLeftDrive = leftStick.getY();
    	double tankRightDrive = rightStick.getY();
    	
    	double tankLeftStrafe = leftStick.getX();
    	double tankRightStrafe = rightStick.getX();

    	double strafe = 0.0;

    	tankLeftDrive = joystickInputConditioning(tankLeftDrive, cautionInput, minInputDriver, maxInputDriver);
    	tankRightDrive = joystickInputConditioning(tankRightDrive, cautionInput, minInputDriver, maxInputDriver);
    	
    	tankLeftStrafe = joystickInputConditioning(tankLeftStrafe, cautionInput, minInputDriver, maxInputDriver);
    	tankRightStrafe = joystickInputConditioning(tankRightStrafe, cautionInput, minInputDriver, maxInputDriver);

    	if (tankRightStrafe >= minInputDriver) {			// This line gives the right joystick strafing priority if both joysticks are moved in the x-direction.
    		strafe = tankRightStrafe;
    	}
    	else {
    		strafe = .5 * tankLeftStrafe;
    	}    	
    	accmss = accg.getX()*9.81;
    	speedms = accmss * .001;
    	frontLeft.set(-tankLeftDrive + strafe);
    	backLeft.set(-tankLeftDrive - strafe);
    	backRight.set(tankRightDrive - strafe);
    	frontRight.set(tankRightDrive + strafe);
    }

    public void Elevator() {
    	
		double elevation = thirdStick.getRawAxis(2);
		
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
    	elevation = joystickInputConditioning(elevation, cautionInput, minInputElevation, maxInputElevation);
    	    	
    	if (upperElevatorLimitSwitch.get() && elevation > 0){
    		elevation = 0;
    	}
    	else if (lowerElevatorLimitSwitch.get() && elevation < 0){
    		elevation = 0;
    	}

    	if (elevation==0) {

    		brake.setSafetyEnabled(true);
    	}
    	else {
    		brake.setSafetyEnabled(false);
    		brake.set(1);
    		Timer.delay(.25);
    		brake.setSafetyEnabled(true);
    	}
    	
    	elevator1.set(elevation);
    	elevator2.set(elevation);
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
    /*
	public void autoTest() throws InterruptedException{
		double t1 = 0;
		double v1 = 0;
		double distance = 0;
		time.start();
		while(!(distance >= autogo) && isEnabled()){
			frontLeft.set(.5);
			backLeft.set(.5);
			backRight.set(.5);
			frontRight.set(.5);
			
			v1 = accg.getX()*981;
			// Calcutates distance with conversion rate for var wt
			distance += v1*Math.pow(wt*.001,2);
			count++;
			time.wait(wt);
		}
		hertz = count/time.get();
		SmartDashboard.putNumber("Auto Hz", hertz);
    	SmartDashboard.putNumber("Auto Count", count);
		frontLeft.set(0);
		backLeft.set(0);
		backRight.set(0);
		frontRight.set(0);
	}
	*/
	
    public void rackMethod(){ 	// Lauren&Margaret&Emma wrote this part
    	
    	double arms = thirdStick.getRawAxis(4);
    	
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
   
    	arms = joystickInputConditioning(arms, cautionInput, minInputRack, maxInputRack);
    	
    	if (outerLimitSwitch.get() && arms > 0){
    		arms = 0;
    	}
    	else if (innerLimitSwitch.get() && arms < 0){
    		arms = 0;
    	}
    	
    	rackPinion.set(arms);    	
    }
    public void autoStrafe(){
    	
    	int counter = 0;
    	
    	frontLeft.setSafetyEnabled(false);
    	frontRight.setSafetyEnabled(false);
    	backLeft.setSafetyEnabled(false);
    	backRight.setSafetyEnabled(false);
    	
        while (counter < 4) {
        	frontRight.set(.5);
        	backLeft.set(.5);
        	frontLeft.set(-.5);
        	backRight.set(-.5);
        	
        	leftSensor.scan();
        	rightSensor.scan();
        	
        	if ( (leftSensor.scan() <= 50) && (leftSensor.scan() > 0) ) {
        		counter = counter + 1;
        	}
        	else {
        		
        	}
        	
        	// if count is 3, slow down to half speed
        	if (counter == 3) {
        		frontRight.set(.25);
            	backLeft.set(.25);
            	frontLeft.set(-.25);
            	backRight.set(-.25);
        	}
        }
        
        
        frontRight.set(0);
    	backLeft.set(0);
    	frontLeft.set(0);
    	backRight.set(0);
    	
    	counter = 0;
    }
    /**
     * Runs during test mode
     */
    
    
    
    public void autonomousA() {
    	RobotDrive myRobotDrive = new RobotDrive(0, 1, 2, 3);
    	Gyro gyro = new Gyro(0);
    	gyro.initGyro();
    	//TODO: gyro sampling rate
    	
    	// minimum input for rack to prevent the bin from slipping
    	rackPinion.set(-.02);
    	
    	// lift the bin
    	elevator1.set(.5);
    	Timer.delay(.5);
    	
    	// move foward to tote
    	while(ultrasonic.getRangeMM() < 500) {
    		myRobotDrive.mecanumDrive_Cartesian(.25, 0, 0, gyro.getAngle());
    	}
    	myRobotDrive.stopMotor();
    	
    	// lower bin onto the tote
    	elevator1.set(-.25);
    	Timer.delay(.8);
    	
    	// open arms
    	rackPinion.set(.25);
    	Timer.delay(.3);
    	rackPinion.stopMotor();
    	
    	// move arms down to the tote level
    	elevator1.set(-.5);
    	Timer.delay(.2);
    	elevator1.stopMotor();
    	
    	// close arms
    	rackPinion.set(.3);
    	Timer.delay(.25);
    	rackPinion.set(-.02);
    	
    	// lift the tote
    	elevator1.set(.4);
    	Timer.delay(.1);
    	elevator1.stopMotor();
    	
    	// rotate the robot
    	while (gyro.getAngle() < 90) {
    		myRobotDrive.mecanumDrive_Cartesian(0, 0, .25, gyro.getAngle());
    	}
    	myRobotDrive.stopMotor();
    	
    	// drive the robot forward into the auto zone
    	while(ultrasonic.getRangeMM() < 500) {
    		myRobotDrive.mecanumDrive_Cartesian(.25, 0, 0, gyro.getAngle());
    	}
    	myRobotDrive.stopMotor();
    	
    	
    }
    public void test() {
    }
}

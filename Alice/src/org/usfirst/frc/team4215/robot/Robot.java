package org.usfirst.frc.team4215.robot;


import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Solenoid;
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
	Joystick elevationStick = new Joystick(2);
	
	// Talon def
	Talon frontLeft = new Talon(0);
	Talon backLeft = new Talon(1);
	Talon backRight = new Talon(2);			// I changed this back to port 2.
	Talon frontRight = new Talon(3);
	

	Talon elevator = new Talon(4);
	Talon rackPinion = new Talon(5);
	
	DigitalInput outerLimitSwitch = new DigitalInput(1);
	DigitalInput innerLimitSwitch = new DigitalInput(2);
	DigitalInput upperElevatorLimitSwitch = new DigitalInput(1);
	DigitalInput lowerElevatorLimitSwitch = new DigitalInput(2);
	
	Solenoid solenoid = new Solenoid(1);
	
	double tankLeft;
	double tankRight;
	double strafe;
	
	private double MAXINPUT = .75;
    private double MININPUT = .15;
	boolean unlocked = true;
	
    /**
     * Drive left & right motors for 2 seconds then stop
     */
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	AnalogInput test = new AnalogInput(0);
    public void autonomous() {
        while (!isOperatorControl() && isEnabled()) {
        	AutonomousStrafe();
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
        
        while (isOperatorControl() && isEnabled()) {
            drivingMethod();
        }
    }
    
    public void drivingMethod(){
    	tankLeft = leftStick.getY();
    	tankRight = rightStick.getY();
    	if (rightStick.getX() >= MININPUT) {			// This line gives the right joystick strafing priority if both joysticks are moved in the x-direction.
    		strafe = rightStick.getX();
    	}
    	else {
    		strafe = .5*leftStick.getX();
    	}
    	
    	
    	// This moves the wheels on the left side of the robot.
    	//tankLeft = Math.floor(tankLeft*20)/20;
    	if (tankLeft <= Math.abs(MININPUT)) {
    		tankLeft = 0;
    	}
    	else if (tankLeft >= MAXINPUT) {
    		tankLeft = MAXINPUT;
    	}
    	else if (tankLeft <= -MAXINPUT) {
    		tankLeft = -MAXINPUT;
    	}
    	
    	// This moves the wheels on the right side of the robot.
    	//tankRight = Math.floor(tankRight*20)/20;
    	if (tankRight <= Math.abs(MININPUT)) {
    		tankRight = MININPUT;
    	}
    	else if (tankRight >= MAXINPUT) {
    		tankRight = MAXINPUT;
    	}
    	else if (tankRight <= -MAXINPUT) {
    		tankRight = -MAXINPUT;
    	}
    	
    	// This controls the strafing.
    	if (strafe <= Math.abs(MININPUT)) {
    		strafe = 0;
    	}
    	else if (strafe >= MAXINPUT) {
    		strafe = MAXINPUT;
    	}
    	else if (strafe <= -MAXINPUT) {
    		strafe = -MAXINPUT;
    	}
    	
    	
    	frontLeft.set(-tankLeft + strafe);
    	backLeft.set(-tankLeft - strafe);
    	backRight.set(tankRight - strafe);
    	frontRight.set(tankRight + strafe);
    }

    public void Elevator() {
    	if (thirdStick.getRawButton(0) && unlocked) {
    		unlocked = false;
    	}
    	if (thirdStick.getRawButton(0) && unlocked==false);
    		unlocked = true;
    	if (unlocked == false) {
    		solenoid.set(true);
    	}
    	else {
    		solenoid.set(false);
    	}
 // solenoid code ends here and elevator code starts here
    	
    	double elevation;
    	final double maxInputElevation = 0.75;
    	final double minInputElevation = 0.15;
    	final double maxNegInputElevation=-.75;
    	final double minNegInputElevation=-.15;
    	    	
    	elevation = thirdStick.getY();
    	
    	if (elevation >= maxInputElevation){
    		elevation = maxInputElevation;
    	}
    	else if (elevation <= Math.abs(minInputElevation)){
    		elevation = minInputElevation;
    	}
    	if (elevation <= maxNegInputElevation){
    		elevation = maxNegInputElevation;
    	}
    	else if (elevation >= Math.abs(minNegInputElevation)){
    		elevation = minNegInputElevation;
    	} 
    	
    	if (outerLimitSwitch.get() && elevation > 0){
    		elevation = 0;
    	}
    	else if (innerLimitSwitch.get() && elevation > 0){
    		elevation = 0;
    	}
    	if (unlocked==false) {
    		elevation=0;
    	}
    	elevator.set(elevation);  
    	
    }    
    


    public void rackMethod(){ 	// Lauren&Margaret&Emma wrote this part
    	double arms;
    	final double maxInputArms = 0.75;
    	final double minInputArms = 0.15;
    	    	
    	arms = thirdStick.getX();
    	
    	if (arms >= maxInputArms){
    		arms = maxInputArms;
    	}
    	else if (arms <= Math.abs(minInputArms)){
    		arms = minInputArms;
    	}
    	
    	if (outerLimitSwitch.get() && arms > 0){
    		arms = 0;
    	}
    	else if (innerLimitSwitch.get() && arms > 0){
    		arms = 0;
    	}
    	
    	rackPinion.set(arms);    	
    }
    
    public void AutonomousStrafe(){
    	
    }
    
    /**
     * Runs during test mode
     */
    public void test() {
    }
}

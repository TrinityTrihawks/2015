
package org.usfirst.frc.team4215.robot;


import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
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
	
	Talon frontLeft = new Talon(0);
	Talon backLeft = new Talon(1);
	Talon backRight = new Talon(4);
	Talon frontRight = new Talon(3);
	
	double tank;
	double tank2;
	double strafe;
	
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
        
    	
        	frontLeft.setSafetyEnabled(false);
        	backLeft.setSafetyEnabled(false);
        	backRight.setSafetyEnabled(false);
        	frontRight.setSafetyEnabled(false);
        	
        	strafeLeft();
        	Timer.delay(1.6);
        	stop();
        
    	
    }
    
    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        
        while (isOperatorControl() && isEnabled()) {
            drivingMethod();
            SmartDashboard.putNumber("Left Y value",leftStick.getY());
            SmartDashboard.putNumber("Right Y value",rightStick.getY());
            SmartDashboard.putNumber("Strafe value",rightStick.getX());
            SmartDashboard.putNumber("Front Right motor Current",pdp.getCurrent(0));
            SmartDashboard.putNumber("Front Left motor Current",pdp.getCurrent(3));
            SmartDashboard.putNumber("Back Left Motor current", pdp.getCurrent(12));
            SmartDashboard.putNumber("Back Right Motor current", pdp.getCurrent(15));
        }
    }
    
    public void drivingMethod(){
    	
    	tank = leftStick.getY();
    	tank2 = rightStick.getY();
    	strafe = rightStick.getX();
    	
    	frontLeft.set(-tank + strafe);
    	backLeft.set(-tank - strafe);
    	backRight.set(tank2 - strafe);
    	frontRight.set(tank2 + strafe);
    }
    
    public void strafeRight(){
    	
    	frontLeft.set(.5);
    	backLeft.set(-.5);
    	backRight.set(-.5);
    	frontRight.set(.5);
    	
    }
    
    public void strafeLeft(){
    	backLeft.set(.75);
    	backRight.set(.75);
    	frontLeft.set(-.85);
    	frontRight.set(-.85);

    }
    
    public void forward(){
    	frontLeft.set(.5); 
    	frontRight.set(.5);
    	backLeft.set(.5);
    	backRight.set(.5);


    }
    
    public void backward(){
    	frontLeft.set(-.5);
    	backLeft.set(-.5);
    	backRight.set(-.5);
    	frontRight.set(-.5);
    }

    public void stop(){
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

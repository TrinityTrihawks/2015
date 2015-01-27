
package org.usfirst.frc.team4215.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

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
    RobotDrive myRobot;
    Joystick stick;

	// Objects defined for drive train.
	Joystick LeftStick = new Joystick(0);
	Joystick RightStick = new Joystick(1);
	// Joystick ThirdStick = new Joystick(2);
	
	Talon LeftFront = new Talon(0);
	Talon RightFront = new Talon(1);
	Talon LeftBack = new Talon(2);
	Talon RightBack = new Talon(3);
	
	double tankLeft;
	double tankRight;
	double strafe;
	double MinInput = .15;
	double MaxInput = .75;
	
	// Objects defined for rack and pinion.
	Talon RackPinion = new Talon(4);
	
	// Objects defined for elevator.
	Talon Elevator = new Talon(5);

	
    public Robot() {
        myRobot = new RobotDrive(0, 1);
        myRobot.setExpiration(0.1);
        stick = new Joystick(0);
    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
        myRobot.setSafetyEnabled(false);
        myRobot.drive(-0.5, 0.0);	// drive forwards half speed
        Timer.delay(2.0);		//    for 2 seconds
        myRobot.drive(0.0, 0.0);	// stop robot
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {
            myRobot.arcadeDrive(stick); // drive with arcade style (use right stick)
            Timer.delay(0.005);		// wait for a motor update time
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }

    public void Elevator() {
    	
    	Joystick LeftStick = new Joystick(0);
    	Talon ElevatorMotor = new Talon(5);
    	double UpDown;
    	
    	if (LeftStick.getRawButton(1)) {
			UpDown = 1;
			ElevatorMotor.set(UpDown);
    	}
    
    	else if (LeftStick.getRawButton(2)) {
    		UpDown = -1;
    		ElevatorMotor.set(UpDown);
    }
    	else {
    		UpDown = 0;
    		ElevatorMotor.set(UpDown);
    	}
    }
}

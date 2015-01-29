package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;


public class Robot extends SampleRobot {
	public static void main(String[] args) {
		
	}
	
	// Objects defined for drive train.
	Joystick LeftStick = new Joystick(0);
	Joystick RightStick = new Joystick(1);
	
	Talon LeftFront = new Talon(0);
	Talon RightFront = new Talon(1);
	Talon LeftBack = new Talon(2);
	Talon RightBack = new Talon(3);
	
	double tankLeft;
	double tankRight;
	double strafe;
	
	// Objects defined for rack and pinion.
	Talon RackPinion = new Talon(4);
	
	// Objects defined for elevator.
	Talon Elevator = new Talon(5);
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    }
    

    public void DriveMethod() {
    	
    	// Tank Drive for teleop...
    	
    	

    		// LeftStick's y-axis controls the left half of the robot.
    		if (LeftStick.getY()>.8) {
    			tankLeft = 1;
    		}
    		else if ((LeftStick.getY()<.1) && (LeftStick.getY()>-.1)) {
    			tankLeft = 0;
    		}
    		else if (LeftStick.getY()<-.8) {
    			tankLeft = -1;
    		}
    		else {
    			tankLeft = LeftStick.getY();
    		}
    		

    		// RightStick's y-axis controls the right half of the robot.
    		if (RightStick.getY()>.8) {
    			tankRight = 1;
    		}
    		else if ((RightStick.getY()<.1) && (RightStick.getY()>-.1)) {
    			tankRight = 0;
    		}
    		else if (RightStick.getY()<-.8) {
    			tankRight = -1;
    		}
    		else {
    			tankRight = RightStick.getY();
    		}


    		// 
    		if ((LeftStick.getX()<-.1) || (LeftStick.getX()>.1)) {
    			strafe = .5*LeftStick.getX();
    		}
    		if ((RightStick.getX()<-.2) || (RightStick.getX()>.2)){
    			strafe = RightStick.getX();
    		}
    		
    		
    		
    		RightFront.set(tankRight + strafe);
    		RightBack.set(tankRight - strafe);
    		
    		LeftFront.set(tankLeft - strafe);
    		LeftBack.set(tankLeft + strafe);
    	}


    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    	while (isOperatorControl() && isEnabled()) { 
    		
    	}
    
    }
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
    }
    
}

package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.Talon;

public class AutonomousMove {
	
	Talon frontL = new Talon(0);
	Talon backL = new Talon(1);
	Talon backR = new Talon(2);
	Talon frontR = new Talon(3);

	public void xAxis (double speed) {
		frontL.set(speed);
		backL.set(-speed);
		backR.set(speed);
		frontR.set(-speed);	
	}
	public void yAxis (double speed) {
		frontL.set(speed);
		backL.set(speed);
		backR.set(speed);
		frontR.set(speed);
	}
	
}		

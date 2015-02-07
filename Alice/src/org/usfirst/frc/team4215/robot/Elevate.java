package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.Talon;

public class elevator {
	Talon elevatorMotor;
	double minLev = 0;
	double maxLev = 10;
	SinglePoint height = new SinglePoint(2, 0, 1);
	
	public elevator(int channel) {
		elevatorMotor = new Talon(channel);
	}
	
	public void move(double cm) throws Exception{
		if(height.scan() > maxLev || height.scan() < minLev){
			Exception e = new Exception();
			throw e;
		}
		
		if(height.scan() < cm){
			while(!(height.scan() >= cm)){
				elevatorMotor.set(.5);
			}
			elevatorMotor.set(0);
		}
		else if(height.scan() > cm){
			while(!(height.scan() <= cm)){
				elevatorMotor.set(.5);
			}
			elevatorMotor.set(0);
		}
		
	}
	
}

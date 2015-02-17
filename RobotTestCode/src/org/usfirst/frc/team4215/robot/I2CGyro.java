package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;

public class I2CGyro {

	private I2C gyro = new I2C(I2C.Port.kOnboard, 0x68);
	private double degrees = 0;
	private double timerStart = Timer.getFPGATimestamp();
	
	public  I2CGyro() {
		gyro.write(0x6B, 0x03); // Power
	   	gyro.write(0x1A, 0x18); // Basic Config
	   	gyro.write(0x1B, 0x00); // Gyro Configa
	}
	
	public void Init() {
    	timerStart = Timer.getFPGATimestamp();
    	degrees = 0.0;
	
	}
	
	public double getDegrees() {
		double timerDelta = Timer.getFPGATimestamp() - timerStart;
		byte[] angle = new byte[1];
		gyro.read(0x47, 1, angle);
		
		double rotation = (angle[0] * timerDelta) * 2;
		degrees += rotation;
		
		return -degrees;
	}

}

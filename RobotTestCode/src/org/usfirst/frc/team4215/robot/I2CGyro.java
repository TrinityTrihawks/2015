package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.RobotBase;

public class I2CGyro {

	private I2C gyro = new I2C(I2C.Port.kOnboard, 0x68);
//	private I2C gyro = ;
	private static double degrees = 0;
	
	public  I2CGyro() {
		gyro.write(0x6B, 0x03);
	   	gyro.write(0x1A, 0x18);
	   	gyro.write(0x1B, 0x00);
	}
	
	public double getDegrees(double deltaTime) {
		byte[] angle = new byte[1];
		gyro.read(0x47, 1, angle);
		
		double rotation = (angle[0] * deltaTime) * 2;
		degrees += rotation;
		
		return -degrees;
	}

}

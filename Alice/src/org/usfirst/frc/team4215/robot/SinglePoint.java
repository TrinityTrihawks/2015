package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.AnalogInput;
/*
 * Class for interpreting data from dt35 SICK sensor
 * set to analog output. 
 */

public class SinglePoint {
	int channel;
	double centimetre = 90;
	double offset;
	int unitsoflength;
	AnalogInput lasr = new AnalogInput(channel);
	
	public SinglePoint( int setChannel, double setOffset, int setUnitsoflength) {
		channel = setChannel;
		offset = setOffset;
		unitsoflength = setUnitsoflength;
	}
	
	/*
	 * Method for retrieving distance based on voltage
	 * from the Single Point laser sensor.  
	 */
	
	public double scan(){
		double volt;
		double distance = offset;
		
		 volt = lasr.getVoltage();
		 
		 switch(unitsoflength){
		 			//Metric
		 case 1: distance += ((volt*centimetre)+50);
		 			break;
		 		
		 			//(Light/Time)ic
		 case 2: distance += (((volt*centimetre)+50))*.0033335696;
				 	break;
				 
				 	//Barbaric
		 case 3: distance += (((volt*centimetre) + 50))*.0393701;
		 		 	break;
		 }
		 
		return distance;
	}
	
	//Get/Set methods for Type
	public String getType(){
		String getType = "";
		
		switch(unitsoflength){
		case 1: getType = "Metric";
					break;
		case 2: getType = "(Light/Time)ic";
					break;
		case 3: getType = "Barbaric";
					break;
		}
		getType += " System";
		 
		return getType;
	}
	
	//Get/Set methods for Metre 
	public double getMetre(){
		return centimetre;
	}
	
	
	//Get method for Channel 
	public int getChannel(){
		return lasr.getChannel();
	}
}

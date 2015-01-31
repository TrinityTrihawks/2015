package org.usfirst.frc.team4215.robot;

import edu.wpi.first.wpilibj.AnalogInput;
/*
 * Class for interpreting data from dt35 SICK sensor
 * set to analog output. 
 */

public class singlePoint {
	int channel;
	double metre = 99990;
	double offset;
	int unitsoflength;
	AnalogInput lasr = new AnalogInput(channel);
	
	public singlePoint( int setChannel, double setOffset) {
		channel = setChannel;
		offset = setOffset;
	}
	
	/*
	 * Method for retrieving distance based on voltage
	 * from the Single Point laser sensor.  
	 */
	
	public double Scan(){
		double volt;
		double distance = offset;
		
		 volt = lasr.getVoltage();
		 
		 switch(unitsoflength){
		 			//Metric
		 case 1: distance += (volt*metre)+50;
		 			break;
		 		
		 			//(Light/Time)ic
		 case 2: distance += ((volt*metre)+50)*3.3335696;
				 	break;
				 
				 	//Barbaric
		 case 3: distance += ((volt*metre) + 50)*3.280839895;
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
	public void setType(int setType){
		unitsoflength = setType;
	}
	
	//Get/Set methods for Metre 
	public double getMetre(){
		return metre;
	}
	public void setMetre(double setMetre){
		metre = setMetre;
	}
	
	//Get method for Channel 
	public int getChannel(){
		return lasr.getChannel();
	}
}

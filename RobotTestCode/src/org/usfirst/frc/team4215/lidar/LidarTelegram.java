package org.usfirst.frc.team4215.lidar;

public class LidarTelegram {
	//
	public static char STX = 0x02;
	public static char ETX = 0x03;
	
	//
	public static String COMMANDTYPE_SINGLE = "sRN";
	public static String COMMANDTYPE_CONTINUOUS = "sEN";
	public static String COMMAND_START = "1";
	public static String COMMAND_END = "0";
	public static String COMMAND_DATAREQUEST = "LMDscandata";
	
	//
}

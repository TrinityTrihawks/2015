package org.usfirst.frc.team4215.lidar;

import org.usfirst.frc.team4215.lidar.LidarTelegram;

public class LidarRequestBuilder
{	
	public String StatusRequest()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(LidarTelegram.STX);	
		sb.append(LidarTelegram.ETX);	
		return sb.toString();
	}

	public String StartContinuousMeasuredOutput()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(LidarTelegram.STX);
		sb.append(LidarTelegram.COMMANDTYPE_CONTINUOUS);
		sb.append(LidarTelegram.COMMAND_DATAREQUEST);
		sb.append(LidarTelegram.COMMAND_START);
		sb.append(LidarTelegram.ETX);	
		return sb.toString();
	}

	public String EndContinuousMeasuredOutput()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(LidarTelegram.STX);
		sb.append(LidarTelegram.COMMANDTYPE_SINGLE);
		sb.append(LidarTelegram.COMMAND_DATAREQUEST);
		sb.append(LidarTelegram.COMMAND_END);
		sb.append(LidarTelegram.ETX);	
		return sb.toString();
	}
	
	public String StartSingleMeasuredOutput() {
		StringBuilder sb = new StringBuilder();
		sb.append(LidarTelegram.STX);
		sb.append(LidarTelegram.COMMANDTYPE_SINGLE);
		sb.append(LidarTelegram.COMMAND_DATAREQUEST);
		sb.append(LidarTelegram.ETX);	
		return sb.toString();		
	}
	
	public String SetLMSMeasurementMode(int meterMode) {
		StringBuilder sb = new StringBuilder();
		sb.append(LidarTelegram.STX);
		sb.append(LidarTelegram.COMMANDTYPE_SINGLE);
		sb.append(LidarTelegram.COMMAND_DATAREQUEST);
		sb.append(LidarTelegram.ETX);	
		return sb.toString();		
	}
}

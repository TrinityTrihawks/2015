package org.usfirst.frc.team4215.lidar;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

public class LidarResponseTelegram extends LidarTelegram{
	
	// continuous response fields			// BYTES	//Total
	/*
	private char TelegramSTX				// 0?1
 	private String commandType; 			// 3
	private int commandTypeSize = 			3;
	private String command; 				// 11		14
	private int commandSize = 				11;
	private int firmwareVersion;	 		// 2		16
	private int firmwareVersionSize = 		2;
	private int deviceID;					// 2		18
	private int deviceIDSize = 				2;
	private long serialNumber;				// 4		22
	private int serialNumberSize = 			4;
	private int deviceStatus;				// 2 x 2	26
	private int deviceStatusSize = 			4;
	private int telegramCounter;			// 2		28
	private int telegramCounterSize = 		2;
	private int scanCounter;				// 2		30
	private int scanCounterSize = 			2;
	private long timeSinceStartup;			// 4		34
	private int timeSinceStartupSize = 		4;
	private long timeOfTransmission;		// 4		38
	private int timeOfTransmissionSize = 	4;
	private short inputStatus;				// 2 x 2	42
	private int inputStatusSize = 			4;
	private short outputStatus;				// 2 x 2	46
	private int outputStatusSize = 			4;
	private long reservedByteA; 			// 2 		48
	private int reservedByteASize = 		2;
	private long scanningFrequency; 		// 4		52
	private int scanningFrequencySize = 	4;
	private long measurementFrequency;		// 4		56
	private int measurementFrequencySize = 4;
	private int numberOfEncoders;			// 2		58
	private int numberOfEncodersSize = 		2;
	private int numberOf16BitChannels;		// 2		60
	private int numberOf16BitChannelsSize = 2;
	*/
	private String measureDataContents;		// 5		65
	private int measureDataContentsSize = 	5;
	private double scalingFactor;			// 4		69
	private int scalingFactorSize = 		4;
	private double scalingOffset;			// 4		73
	private int scalingOffsetSize = 		4;
	private long startingAngle;				// 4		77
	private int startingAngleSize = 		4;
	private int angularStepWidth;			// 2		79
	private int angularStepWidthSize = 		2;
	private int numberOfDataPoints = 0;		// 2		81
	private int numberOfDataPointsSize =	2;
	private ArrayList<Integer> dataPoints = new ArrayList<Integer>();  // 2
	private int dataPointsSize = 			2;
	/*
	private int numberOf8BitChannels;		// 2		81 + dataPointsSize * Count
	private int numberOf8BitChannelsSize = 	2;
	private int position;					// 2		83 + dataPointsSize * Count
	private int positionSize = 				2;
	private int outputsDeviceName;			// 2		85 + dataPointsSize * Count
	private int outputsDeviceNameSize = 	2;
	private int outputsComment;				// 2		87 + dataPointsSize * Count
	private int outputsCommentSize = 		2;
	private int outputsTimeInformation;		// 2		89 + dataPointsSize * Count
	private int outputsTimeInformationSize = 2;
	private int outputsEventInformation;	// 2		91 + dataPointsSize * Count
	private int outputsEventInformationSize = 2;
	private int outputsEventInformationSize = 2;
	private char TelegramETX;
	*/
	
    public void parseContinousMeasuredOutput(byte[] payload) {
        if(payload == null || payload.length < 81) {
        	return;
        }
        
        try {
	        // suppose we just want to find the output channels (data points)
	        int offset = 65;
	        measureDataContents = new String(payload, offset, measureDataContentsSize);
	        offset += measureDataContentsSize;

	        scalingFactor = ByteBuffer.wrap(payload, offset, scalingFactorSize).getDouble();
	        offset += scalingFactorSize;
	        
	        scalingOffset = ByteBuffer.wrap(payload, offset, scalingOffsetSize).getDouble();
	        offset += scalingOffsetSize;
	        
	        startingAngle = ByteBuffer.wrap(payload, offset, startingAngleSize).getInt();
	        offset += startingAngleSize;

	        angularStepWidth = ByteBuffer.wrap(payload, offset, angularStepWidthSize).getInt();
	        offset += angularStepWidthSize;

	        numberOfDataPoints = ByteBuffer.wrap(payload, offset, measureDataContentsSize).getInt();
	        offset += numberOfDataPointsSize;
	        
	        int i = 0;
	        while (i < numberOfDataPoints && offset+dataPointsSize < payload.length) {
	        	dataPoints.add(ByteBuffer.wrap(payload, offset, dataPointsSize).getInt());
	        	offset += dataPointsSize;
	        	i++;
	        }
        } catch (Exception e) {
        	// send error back to RioLog
        	
        	// reset datapoints array
        	dataPoints = new ArrayList<Integer>();
        }        
        
        return;
    }
    
	public String getMeasureDataContents() {
		return measureDataContents;
	}

	public double getScalingFactor() {
		return scalingFactor;
	}

	public double getScalingOffset() {
		return scalingOffset;
	}

	public long getStartingAngle() {
		return startingAngle;
	}

	public int getAngularStepWidth() {
		return angularStepWidth;
	}

    public ArrayList<Integer> getDataPoints() { return dataPoints; }
    
	public int getNumberOfDataPoints() {
		return numberOfDataPoints;
	}


}

package com.va4sh.logreader.interpreters;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.va4sh.logreader.data.VeraVariable;

public class VariableInterpreter {
	//<Event Description="Tripped by report" Time="14-02-08 11:20:52" Node="2" Device="3" NodeType="ZWaveBinarySensor" NodeDescription="urn:schemas-micasaverde-com:device:MotionSensor:1" CommandClass="30" Command="3" Value="0"/> <0x402>
	private static final Logger LOGGER = Logger.getLogger( VariableInterpreter.class.getName() );

	static {
		LOGGER.setLevel(Level.ALL);
	}

	VeraVariable translateVariable(String line){
		VeraVariable variable = new VeraVariable();
		String[] splittedLine = line.split("\\s+");
		if((splittedLine!= null) && (splittedLine.length > 14)){
			variable.setDate(changeDate(splittedLine[1]));
			variable.setTime(changeTime(splittedLine[2]));
			variable.setDeviceVariable(splittedLine[3]);
			variable.setDeviceId(splittedLine[5]);
			variable.setService(splittedLine[7]);
			variable.setVariable(splittedLine[9].replaceAll("(.*?)m",""));
			variable.setOldValue(splittedLine[11]);
			variable.setNewValue(splittedLine[13]);
		}
		return variable;
	}



	public String changeDate(String date){
		String[] d = date.split("/");

		if(d.length > 2){ 	 
			return "20"+d[2]+"-"+d[0]+"-"+d[1]; 

		}else{
			LOGGER.log( Level.WARNING, "Date error: "+date);
		}
		return null;
	}
	public String changeTime(String time){
		SimpleDateFormat veraFormat=new SimpleDateFormat("HH:mm:ss");
		String localTime=veraFormat.format(new Date());
		return localTime;
	}
	


}

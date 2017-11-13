package com.va4sh.logreader.interpreters;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.va4sh.logreader.data.VeraEvent;
import com.va4sh.ssh.SSHManager;

public class EventInterpreter {
	//<Event Description="Tripped by report" Time="14-02-08 11:20:52" Node="2" Device="3" NodeType="ZWaveBinarySensor" NodeDescription="urn:schemas-micasaverde-com:device:MotionSensor:1" CommandClass="30" Command="3" Value="0"/> <0x402>
	private static final Logger LOGGER = Logger.getLogger( SSHManager.class.getName() );

	static {
		LOGGER.setLevel(Level.ALL);
	}

	VeraEvent translateEvent(String line){
		VeraEvent event = new VeraEvent();
		boolean isOk = true;

//		System.out.println("event line: " + line);
		
		String value = interpretDevice(line);
		if(value != null) {
			event.setDevice(interpretDevice(line));
		}
		else isOk = false;

		value = interpretValue(line);
		if(value != null) {
			event.setStatus(value);
		}
		else isOk = false;

		value = interpretTime(line);
		if(value != null){
			String[] time = value.split(" ");
			if(time.length > 1){
				LOGGER.log( Level.INFO, "Date: "+time[0]);
				event.setDate( changeDate(time[0]) );
				LOGGER.log( Level.INFO, "Time: "+time[1]);				
				event.setTime(time[1]);
			}
		}		else isOk = false;
		if(!isOk) {
			event = null;
		}
		return event;
	}

	public String changeDate(String date){
		String[] d = date.split("-");

		if(d.length > 2){ 	 
			return d[2]+"-"+d[1]+"-"+d[0]; 

		}else{
			LOGGER.log( Level.WARNING, "Date error: "+date);
		}
		return null;
	}

	private String interpretTime(String line) {
		Pattern p = Pattern.compile("Time=\"(.*?)\"");
		Matcher m = p.matcher( line );

		if(m.find()){ 	 
			return m.group(1); 

		}else{
			LOGGER.log( Level.WARNING, "No time set");
		}
		return null;
	}

	private String interpretValue(String line){
		Pattern p = Pattern.compile("now=\"(.*?)\"");
		Matcher m = p.matcher( line );

		if(m.find()){ 	 
			return m.group(1); 

		}else{
			LOGGER.log( Level.WARNING, "No value in line: "+line);
		}
		return null;
	}

	private String interpretDevice(String line){
		Pattern p = Pattern.compile("Device=\"(.*?)\"");
		Matcher m = p.matcher( line );

		if(m.find()){ 	 
			return m.group(1); 

		}
		return null;
	}

}

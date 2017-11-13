package com.va4sh.logreader.data;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VeraEvent {
  
    private static final Logger LOGGER = Logger.getLogger( VeraEvent.class.getName() );	
	private String device;
	private boolean status;
	private String time;
	private String date;
	
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(String state) {
		if(state!= null){
			if("255".equalsIgnoreCase(state)){
			  this.status = true;
			}
			else{
			  this.status = false;
			}
		}else{
		  LOGGER.log( Level.WARNING, "INVALID EVENT FROM DEVICE: "+device);
		}
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setStatus(boolean state) {
		this.status = state;
	}
	
	

}

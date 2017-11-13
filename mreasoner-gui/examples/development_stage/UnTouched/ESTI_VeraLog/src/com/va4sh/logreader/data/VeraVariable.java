package com.va4sh.logreader.data;

import java.lang.reflect.Field;

import com.va4sh.utils.Constants;

public class VeraVariable {
	public static final String VAR_TRIPPED = "Tripped";
	private String date;
	private String time;
	private String deviceVariable;
	private String deviceId;
	private String service;
	private String variable;
	private String oldValue;
	private String newValue;
		
	public VeraVariable(){
		this.date = "";
		this.time = "";
		this.deviceVariable = "";
		this.deviceId = "";
		this.service = "";
		this.variable = "";
		this.oldValue = "";
		this.newValue = "";
				
	}
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getDeviceVariable() {
		return deviceVariable;
	}
	
	public void setDeviceVariable(String deviceVariable) {
		this.deviceVariable = deviceVariable;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getVariable() {
		return variable;
	}
	
	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getOldValue() {
		return oldValue;
	}
	
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	
	public String getNewValue() {
		return newValue;
	}
	
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public boolean isEmpty(){
	    boolean result = true;
	    for(Field field : VeraVariable.class.getFields()){
	      result = result && "".equals(field.getType().toString());
	    }
	    return result;

	}
	
	public String toString() {
		return date + "T" + time +
				//" device=" + deviceId +
				//" service=" + service +
				"," +Constants.deviceID.get(deviceId)+
				"," +Constants.deviceStatus.get(deviceId)+
				"," +Constants.deviceState.get(deviceId);
				//" [" + variable + "] "+
				//" was:" + oldValue +
				//" now:" + newValue;
				
	}
}

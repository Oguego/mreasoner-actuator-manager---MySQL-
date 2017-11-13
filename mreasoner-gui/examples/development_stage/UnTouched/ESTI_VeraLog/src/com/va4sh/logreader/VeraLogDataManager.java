package com.va4sh.logreader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import com.va4sh.logreader.data.VeraData;
import com.va4sh.logreader.data.VeraVariable;
import com.va4sh.logreader.interpreters.VeraInterpreter;
import com.va4sh.ssh.SSHManager;
import com.va4sh.utils.Constants;

public abstract class VeraLogDataManager extends Observable implements Observer {

	protected VeraInterpreter interpreter;


	public VeraLogDataManager(){
		interpreter = new VeraInterpreter();
	}

	public void update(Observable o, Object arg) {
		if( o instanceof SSHManager ) {
			readAndStoreLine( (String)arg );
		}
	}

	public void readAndStoreLine(String line){
		//		System.out.println(line);
		VeraData  data = readLine(interpreter, line);
		if(data != null){
			storeData(data);
		}
	}

	private VeraData readLine(VeraInterpreter interpreter, String line) {
		VeraData data = new VeraData();
		if (line != null){
			data = interpreter.interpret(line);
		}
		return data;
	}

	private void storeData(VeraData data) {
		switch(data.getId()){
		case 6:
			storeVeraVariable(data);
			if (data.getVariable().getVariable().contentEquals("Status")|(data.getVariable().getVariable().contentEquals("Tripped"))){
				
				if((data.getVariable().getOldValue().equals("0"))&& (data.getVariable().getNewValue().equals("1"))){
					Constants.deviceStatus.put(data.getVariable().getDeviceId(), "ON");
					Constants.deviceState.put(data.getVariable().getDeviceId(), "100");
					
				} else if ((data.getVariable().getOldValue().equals("1"))&&(data.getVariable().getNewValue().equals("0"))){
					Constants.deviceStatus.put(data.getVariable().getDeviceId(), "OFF");
					Constants.deviceState.put(data.getVariable().getDeviceId(), "0");
				}
				
					
			try{
				File file=new File("results\\results.data");
				if (!file.exists()){
					file.createNewFile();
						}
				FileWriter fw= new FileWriter(file.getAbsoluteFile(),true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter writer = new PrintWriter(bw);
				
					writer.println(data.getVariable());
					writer.close();
			}
			catch(Exception error){
				
				System.out.println("Error Message: " + error.getMessage());
			}
			break;
			}
		case 7:
			storeVeraEvent(data);
			break;
		default:
			break;
		}
	}

	protected long getVariableTimestamp(VeraVariable var) {
		SimpleDateFormat veraFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss.SSS");
		String veraEventTime = var.getDate() + " " + var.getTime();
		
		try {	
			Date date1 = veraFormat.parse(veraEventTime);
			return date1.getTime();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	protected abstract void storeVeraEvent(VeraData data);
	protected abstract void storeVeraVariable(VeraData data);
}

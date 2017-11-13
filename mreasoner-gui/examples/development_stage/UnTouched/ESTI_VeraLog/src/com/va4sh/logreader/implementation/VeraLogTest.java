package com.va4sh.logreader.implementation;

import com.va4sh.logreader.VeraLogDataManager;
import com.va4sh.logreader.data.VeraData;
import com.va4sh.logreader.data.VeraVariable;
import com.va4sh.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeraLogTest extends VeraLogDataManager {
	private static final Logger LOGGER = Logger.getLogger( VeraLogTest.class.getName() );

	public VeraLogTest() {
	}
	
	@Override
	protected void storeVeraEvent(VeraData data) {
	}

	@Override
	protected void storeVeraVariable(VeraData data) {
		VeraVariable var = data.getVariable();

		if( Constants.deviceID.containsKey(var.getDeviceId()) /* valid monitored device */ && 
				(var.getVariable().equals("Status") || var.getVariable().equals("Tripped") )  ) {
			LOGGER.log(Level.INFO, Constants.deviceID.get(var.getDeviceId()) + ": from "+ var.getOldValue() + " to " + var.getNewValue() + "\n");
			
			try {
				SimpleDateFormat veraFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss.SSS");
				SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				
				String veraEventTime = var.getDate() + " " + var.getTime();
				String localTime = localFormat.format(new Date());
				Date date1 = veraFormat.parse(veraEventTime);
				Date date2 = localFormat.parse( localTime );
				long difference = date2.getTime() - date1.getTime();
				difference = (long) Math.floor( (double)difference/1000.0 )-39;
				
//				System.out.println(difference);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        
			setChanged();
			notifyObservers(var);
		}
		
	}
	
	static {
		LOGGER.setLevel(Level.ALL);
	}
}

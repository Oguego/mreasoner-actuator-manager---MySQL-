package com.va4sh.logreader;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.va4sh.logreader.VeraLogDataManager;
import com.va4sh.ssh.SSHManager;

public class VeraLogReader {
	private static final Logger LOGGER = Logger.getLogger( VeraLogReader.class.getName() );
	private static VeraLogReader instance = null;
	private SSHManager sshManager;
	private Thread readerThread;

	public static VeraLogReader getInstance() {
		if( instance==null )
			instance = new VeraLogReader();
		return instance;
	}
	
	private VeraLogReader() {
		sshManager = SSHManager.getInstance();
		sshManager.setCommand("tail -f  /var/log/cmh/LuaUPnP.log\n");
	}

	public void registerObserver(VeraLogDataManager dataManager) {
		sshManager.registerObserver(dataManager);
	}

	public void start(){
		if( readerThread!=null && (readerThread.isAlive() || readerThread.isInterrupted()) )
			return;
		
		readerThread = new Thread(sshManager);
		readerThread.start();
	}

	public void stop() {
		try {
			if( readerThread.isInterrupted() )
				return;
			
			sshManager.interrupt();
			readerThread.interrupt();
			readerThread.join();
		} catch (InterruptedException e) {
			LOGGER.log( Level.SEVERE, "", e );
			Thread.currentThread().interrupt();
		}
	}

	public SSHManager getSshClient() {
		return sshManager;
	}

	public void setSshClient(SSHManager ssh) {
		this.sshManager = ssh;
	}
}

package com.va4sh.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.NoRouteToHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sshtools.net.SocketTransport;
import com.sshtools.ssh.ChannelOpenException;
import com.sshtools.ssh.HostKeyVerification;
import com.sshtools.ssh.SshAuthentication;
import com.sshtools.ssh.SshClient;
import com.sshtools.ssh.SshConnector;
import com.sshtools.ssh.SshException;
import com.sshtools.ssh.SshSession;
import com.sshtools.ssh.components.SshPublicKey;
import com.sshtools.ssh2.Ssh2PasswordAuthentication;
import com.va4sh.ssh.exceptions.AuthenticationException;
import com.va4sh.ssh.exceptions.PartialAuthenticationException;
import com.va4sh.ssh.exceptions.TerminalAllocationException;
import com.va4sh.utils.Constants;

public class SSHManager extends Observable implements Runnable {
	private static final Logger LOGGER = Logger.getLogger( SSHManager.class.getName() );
	private static SSHManager instance = null;
	private SSHConfigs sshConfigs;
	private SshClient ssh;
	private SshConnector con;
	private SshSession session;

	private BufferedReader br;

	private String command;
	boolean running;
	boolean initFinished;
	boolean finalFinished;
	int connectionStatus;

	public static SSHManager getInstance() {
		if( instance==null )
			instance = new SSHManager();
		return instance;
	}

	private SSHManager() {
		LOGGER.setLevel(Level.ALL);
		this.sshConfigs = SSHConfigs.getInstance();

		initFinished = false;
		finalFinished = false;
		running = true;
		connectionStatus = 0;

		command = "";
	}

	private SSHManager(Observer dataManager) {
		this();
		registerObserver(dataManager);
	}

	public void registerObserver(Observer dataManager) {
		if( dataManager!=null )
			addObserver(dataManager);		
	}

	public void setCommand(String cmd) {
		command = cmd;
	}

	public int initialize() throws IOException, SshException {
		con = SshConnector.createInstance();
		// Lets do some host key verification
		HostKeyVerification hkv = new HostKeyVerification() {
			public boolean verifyHost(String hostname, SshPublicKey key) {
				return true;
			}
		};
		con.getContext().setHostKeyVerification(hkv);

		try {			
			ssh = con.connect(new SocketTransport(Constants.hostname, sshConfigs.getPort() ), Constants.hostname, true);
		}
		catch(NoRouteToHostException nrhe) {
			LOGGER.log(Level.SEVERE, "No route to host " + Constants.hostname + ":" + sshConfigs.getPort() );
			return SshAuthentication.FAILED;
		}

		int result = authenticate();
		if( result==SshAuthentication.COMPLETE )
			result = openAndReadLog(result);
		else {
			printSSHConfigsAuthenticationError();
		}
		return result;
	}

	private int openAndReadLog(int result) {
		int finalResult = result; 
		try{
			openSession();	
			finalResult = readLog(finalResult);	  
			checkAuthenticationProtocolState(result);
		} catch(IOException e){
			LOGGER.log( Level.SEVERE, e.getMessage() );
			finalResult = 0;
		} catch(AuthenticationException e){
			LOGGER.log( Level.SEVERE, e.getMessage() );
			finalResult = 0;
		} catch (TerminalAllocationException e) {
			LOGGER.log( Level.SEVERE, e.getMessage() );
			finalResult = 0;
		} catch (SshException e) {
			e.printStackTrace();
			finalResult = 0;
		} catch (ChannelOpenException e) {
			e.printStackTrace();
			finalResult = 0;
		} 

		return finalResult;
	}

	private void checkAuthenticationProtocolState(int result) throws AuthenticationException {
		if (result == SshAuthentication.FURTHER_AUTHENTICATION_REQUIRED ){
			throw new PartialAuthenticationException();
		}
		if (result == SshAuthentication.FAILED){
			throw new AuthenticationException();
		}
	}

	private void openSession() throws IOException, TerminalAllocationException, SshException, ChannelOpenException {
		session = ssh.openSessionChannel();	

		if(!session.requestPseudoTerminal("vt100", 80, 24, 0, 0)){
			throw new TerminalAllocationException();
		}
	}

	private int readLog(int result) throws IOException, SshException {
		int finalResult = result;

		if(session.startShell()) {
			startReadingLog();
		} else{
			LOGGER.log( Level.SEVERE, "Failed to start the users shell");
			finalResult = 0;     
		}
		return finalResult;
	}

	private int authenticate() throws IOException, SshException {
		Ssh2PasswordAuthentication pwd = new Ssh2PasswordAuthentication();

		pwd.setUsername( sshConfigs.getUsername() );
		pwd.setPassword( sshConfigs.getPassword() );

		return ssh.authenticate(pwd);
	}

	private void startReadingLog(String cmd) throws IOException {
		OutputStream out = session.getOutputStream();

		out.write(cmd.getBytes());

		br = new BufferedReader(new InputStreamReader(session.getInputStream(), "US-ASCII"));		
	}

	private void startReadingLog() throws IOException {
		if( !command.equals("") )
			startReadingLog( command );
	}

	private void printSSHConfigsAuthenticationError() {
		LOGGER.log( Level.SEVERE, 
				"AUTHENTICATION ERROR: hostname: "+ Constants.hostname +
				" user: "+sshConfigs.getUsername());
	}

	@Override	
	public void run() {
		try {
			connectionStatus = initialize();
		}
		catch (Exception e) {
			LOGGER.log( Level.SEVERE, "exception on initialization", e );
		}

		if( connectionStatus == SshAuthentication.COMPLETE){
			LOGGER.log(Level.INFO, "Successfully connected");
			main();

			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				LOGGER.log( Level.SEVERE, "interrupt exception", e );
			}
		}
		initFinished = true;
		if ( connectionStatus == SshAuthentication.COMPLETE) {
			while ( accessRunning(false, false) ) {
				if( Thread.currentThread().isInterrupted() ) {
					LOGGER.log(Level.WARNING, "interrupted");
					break;
				}
				main();
			}
			close();
		}


		LOGGER.log( Level.SEVERE, "$ ssh manager finished $" );
	}

	public void interrupt() {
		accessRunning(true, false);
	}

	private void close() {
		try {
			ssh.disconnect();
			session.close();
			Thread.sleep(50);
		}
		catch (InterruptedException e) {
			LOGGER.log( Level.SEVERE, "interrupted exception", e );
		}

		finalFinished = true;
	}

	private void main() {

		try {
			String line = br.readLine();
			if(!sshConfigs.isSilence()){
				setChanged();
				notifyObservers(line);
			}
		}
		catch (IOException e) {
			LOGGER.log( Level.SEVERE, "SSH I/O Exception",e);
		}

	}

	public boolean isInitializationFinished(){
		return initFinished;
	}

	public boolean isFinalizationFinished(){
		return finalFinished;
	}

	public boolean checkConnection() {
		return connectionStatus == SshAuthentication.COMPLETE;
	}

	private synchronized boolean accessRunning(boolean wrt, boolean val) {
		if( wrt )
			running = val;
		return running;
	}
}
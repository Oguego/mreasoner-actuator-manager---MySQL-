package com.va4sh.ssh;

public class SSHConfigs {
	public static SSHConfigs instance = null;
	private int		port = 22;
	private String  username;
	private String  password;
	private boolean silence;

	private SSHConfigs() {
	}
	
	public static SSHConfigs getInstance() {
		if( instance==null )
			instance = new SSHConfigs();
		return instance;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port ) {
		this.port = port;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isSilence(){
		return silence;
	}

	public void setSilence(boolean silence) {
		this.silence = silence;
		
	}
	
}

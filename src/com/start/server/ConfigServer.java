package com.start.server;

public class ConfigServer {
	String logPath ="";
	int tokenTimeout=0;
	int codeTimeout=0;
	public String getLogPath() {
		return logPath;
	}
	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
	public int getTokenTimeout() {
		return tokenTimeout;
	}
	public void setTokenTimeout(int tokenTimeout) {
		this.tokenTimeout = tokenTimeout;
	}
	public int getCodeTimeout() {
		return codeTimeout;
	}
	public void setCodeTimeout(int codeTimeout) {
		this.codeTimeout = codeTimeout;
	}
	
	private static ConfigServer instance;
	public static ConfigServer getInstance() {
		if (instance == null) {
			instance = new ConfigServer();
		}
		return instance;
	   }
	
}

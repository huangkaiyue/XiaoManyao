package com.lanbao.user;

public class AckMessage {
	String msgtype;
	String resdata;
	String token;
	String devsn;
	int result;
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getResdata() {
		return resdata;
	}
	public void setResdata(String resdata) {
		this.resdata = resdata;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getDevsn() {
		return devsn;
	}
	public void setDevsn(String devsn) {
		this.devsn = devsn;
	}
}

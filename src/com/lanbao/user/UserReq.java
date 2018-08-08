package com.lanbao.user;

public class UserReq {
	String msgtype;
	String username;
	String passwd;
	String devsn;
	String token;
	String code;
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getDevsn() {
		return devsn;
	}
	public void setDevsn(String devsn) {
		this.devsn = devsn;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String toString(){
		return " [msgtype=" + msgtype + ", username=" + username+ ", passwd=" + passwd+ ", devsn=" + devsn
				+", token=" + token + ",code=" + token +"]";
	}
}

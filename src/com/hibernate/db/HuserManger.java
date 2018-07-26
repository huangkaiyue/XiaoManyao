package com.hibernate.db;

public class HuserManger {
	private int uId;
	private String usrname;
	private String passwd;
	private String chmod;
	public String getChmod() {
		return chmod;
	}
	public void setChmod(String chmod) {
		this.chmod = chmod;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private String date;
	
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getUsrname() {
		return usrname;
	}
	public void setUsrname(String usrname) {
		this.usrname = usrname;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	@Override
	public String toString() {
		return "HuserManger [uId=" + uId + ", usrname=" + usrname+ ", passwd=" + passwd
				+ ", chmod=" + chmod + ", date=" + date + "]";
	}
}

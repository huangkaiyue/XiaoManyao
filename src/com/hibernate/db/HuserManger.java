package com.hibernate.db;

import java.util.HashSet;
import java.util.Set;

public class HuserManger {
	private int uId;
	private String usrname;
	private String passwd;
	private String devSn;
	private String chmod;
	private String date;
	private Set<HuserDevsnlistUtil> devsnS = new HashSet<HuserDevsnlistUtil>();
	

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
	public String getDevSn() {
		return devSn;
	}
	public void setDevSn(String devSn) {
		this.devSn = devSn;
	}
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
	public Set<HuserDevsnlistUtil> getDevsnS() {
		return devsnS;
	}
	public void setDevsnS(Set<HuserDevsnlistUtil> devsnS) {
		this.devsnS = devsnS;
	}
	@Override
	public String toString() {
		return "HuserManger [uId=" + uId + ", usrname=" + usrname+ ", passwd=" + passwd
				+", devSn="+devSn+ ", chmod=" + chmod + ", date=" + date + "]";
	}
}

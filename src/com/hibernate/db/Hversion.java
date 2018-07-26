package com.hibernate.db;

import java.util.Date;

public class Hversion {
	private int uId;
	private String name;
	private String md5;
	private String message;
	private Date date;
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Hversion [uId=" + uId + ", name=" + name+ ", md5=" + md5+ ", message=" + message
				+", date=" + date + "]";
	}
}

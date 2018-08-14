package com.hibernate.db;

import java.util.Date;

public class WxDevsnlistUtil {
	private int uId;
	private String devsn;
	private Date date;
	private Weixinuser devsn_id;
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getDevsn() {
		return devsn;
	}
	public void setDevsn(String devsn) {
		this.devsn = devsn;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Weixinuser getDevsn_id() {
		return devsn_id;
	}
	public void setDevsn_id(Weixinuser devsn_id) {
		this.devsn_id = devsn_id;
	}
	@Override
	public String toString() {
		return "DevsnlistUtil [uId=" + uId + ", devsn=" + devsn+", date=" + date + "]";
	}
}

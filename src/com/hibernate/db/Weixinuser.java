package com.hibernate.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Weixinuser {
	private int uId;
	private String unionId;
	private String openid;
	private String chmod;
	private Date date;
	private String phone;

	private Set<WxDevsnlistUtil> devsnS = new HashSet<WxDevsnlistUtil>();
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getChmod() {
		return chmod;
	}
	public void setChmod(String chmod) {
		this.chmod = chmod;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Set<WxDevsnlistUtil> getDevsnS() {
		return devsnS;
	}
	public void setDevsnS(Set<WxDevsnlistUtil> devsnS) {
		this.devsnS = devsnS;
	}
	@Override
	public String toString() {
		return "Weixinuser [uId=" + uId + ", unionId=" + unionId+ ", openid=" + openid+ ",  chmod=" + chmod
				+ ", date=" + date + "]";
	}
}

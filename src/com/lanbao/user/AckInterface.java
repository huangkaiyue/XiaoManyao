package com.lanbao.user;

import net.sf.json.JSONObject;

public class AckInterface {
	public static String CreateAckJson(String msgtype,String resdata,String token,int result){
		AckMessage ack = new AckMessage();
		ack.setMsgtype(msgtype);
		ack.setResdata(resdata);
		ack.setResult(result);
		ack.setToken(token);
		JSONObject json = JSONObject.fromObject(ack);
		return json.toString();
	}
	public static String CreateLoginAckJson(String msgtype,String devsn,String resdata,String token,int result){
		AckMessage ack = new AckMessage();
		ack.setMsgtype(msgtype);
		ack.setResdata(resdata);
		ack.setResult(result);
		ack.setToken(token);
		ack.setDevsn(devsn);
		JSONObject json = JSONObject.fromObject(ack);
		return json.toString();
	}
}

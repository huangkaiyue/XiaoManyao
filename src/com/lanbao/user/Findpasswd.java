package com.lanbao.user;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.aliyuncs.exceptions.ClientException;
import com.lanbao.common.Common;
import com.lanbao.dbdata.CheckCode;
import com.lanbao.dbdata.XiaomanyaoInterface;
import com.opensymphony.xwork2.ActionSupport;

public class Findpasswd extends ActionSupport{
	public String findpasswd() throws IOException, ClientException{
		String boby=getRequestBoby();
		if(boby==null||boby.equals("")){
			AckRequestFailed();
			return NONE;
		}
		JSONObject js = JSONObject.fromObject(boby);
		String msgtype = js.getString("msgtype").toString();
		if(msgtype==null){
			AckRequestFailed();
			return NONE;
		}
		System.out.println("recv boby :"+boby);
		if(msgtype.equals("findpasswd")){
			String phone = js.getString("phone").toString();
			String passwd = js.getString("passwd").toString();
			if(phone!=null&&passwd!=null){
				Date day=new Date();    
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				System.out.println("insert register phone:"+phone);
				String date =df.format(day);
				if(XiaomanyaoInterface.updatepasswd(phone, passwd)==0){
					AckUpdateSuccess();
				}else{
					AckRequestFailed();
				}
			}
		}else if(msgtype.equals("phonecode")){
			String status = js.getString("status").toString();
			if(status.equals("getcode")){	//获取验证码
				String phone = js.getString("phone").toString();
				if(XiaomanyaoInterface.checkusername(phone)<0){
					ackGetPhoneCode("0000",201,"账号未注册过");	//回应app
				}else{	
					String code = Common.CreateCode();
					SmsDemo.sendSms(phone, code);	//发送短信验证码
					CheckCode.CachePhoneCode(phone, code);	//保存手机号码和验证码到redis 服务器当中
					ackGetPhoneCode(code,200,"验证码已经发送");	//回应app
				}
			}else if(status.equals("checkcode")){	//校验验证码
				String phone = js.getString("phone").toString();
				String code = js.getString("code").toString();
				int ret = CheckCode.checkPhoneCode(phone, code);
				switch (ret) {
				case CheckCode.ok_code:
					ackCheckcode(200,"校验验证码正确");
					break;
				case CheckCode.timeout_code:
					ackCheckcode(201,"验证码已失效");
					break;
				case CheckCode.invalid_code:
					ackCheckcode(202,"无效的验证码");
					break;
				default:
					break;
				}
			}
		}
		return NONE;
	}
	
	private void ackGetPhoneCode(String code,int result,String resdata) throws IOException{
		JSONObject obj = new JSONObject();
		obj.put("msgtype", "phonecode");
		String token = Common.GetToken();
		obj.put("token", token);
		obj.put("code", code);
		obj.put("resdata", resdata);
		obj.put("result", result);
		System.out.println(obj.toString());
		AckResponse(obj.toString());
	}
	private void ackCheckcode(int result,String resdata) throws IOException{
		JSONObject obj = new JSONObject();
		obj.put("msgtype", "phonecode");
		String token = Common.GetToken();
		obj.put("token", token);
		obj.put("resdata", resdata);
		obj.put("result", result);
		System.out.println(obj.toString());
		AckResponse(obj.toString());
	}
	private void AckUpdateSuccess() throws IOException{
		JSONObject obj = new JSONObject();
		obj.put("msgtype", "findpasswd");
		obj.put("token", Common.GetToken());
		obj.put("resdata", "找回密码成功");
		obj.put("result", 200);
		System.out.println(obj.toString());
		AckResponse(obj.toString());
	}
	
	private void AckRequestFailed() throws IOException{
		JSONObject obj = new JSONObject();
		obj.put("msgtype", "findpasswd");
		obj.put("token", Common.GetToken());
		obj.put("resdata", "无效的参数");
		obj.put("result", 403);
		System.out.println(obj.toString());
		AckResponse(obj.toString());
	}
	
	private void AckResponse(String response) throws IOException{
		HttpServletResponse Response= ServletActionContext.getResponse();
		Response.setHeader("Content-type", "text/html;charset=UTF-8");  
		Response.getWriter().print(response);
		Response.getWriter().flush();
		Response.getWriter().close();
	}
	
	private String getRequestBoby(){
		HttpServletRequest request =ServletActionContext.getRequest();
		int len = request.getContentLength();
//		System.out.println("len = "+len);
		if(len<=0){
			return null;
		}
		ServletInputStream bobySt;
		String str="";
		try {
			bobySt = request.getInputStream();
			byte[] buffer = new byte[len];
			bobySt.read(buffer, 0, len);
			str= new String(buffer);
			System.out.println("getRequestBoby: get len="+len+" boby is :"+str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}

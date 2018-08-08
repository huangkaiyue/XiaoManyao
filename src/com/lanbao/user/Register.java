package com.lanbao.user;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.aliyuncs.exceptions.ClientException;
import com.lanbao.common.Common;
import com.lanbao.common.HttpServletUtils;
import com.lanbao.dbdata.CheckCode;
import com.lanbao.dbdata.XiaomanyaoInterface;
import com.opensymphony.xwork2.ActionSupport;

public class Register extends ActionSupport{
	public String register() throws IOException, ClientException{
		String boby=HttpServletUtils.getRequestBoby(ServletActionContext.getRequest());
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
		if(msgtype.equals("register")){
			String phone = js.getString("phone").toString();
			String passwd = js.getString("passwd").toString();
			if(phone!=null&&passwd!=null){
				System.out.println("insert register phone:"+phone);
				if(XiaomanyaoInterface.InsertData(phone, passwd)==0){
					AckRequestRegisterSuccess();
				}else{
					AckRequestRegisterFailed();
				}
			}
		}else if(msgtype.equals("phonecode")){
			String status = js.getString("status").toString();
			if(status.equals("getcode")){	//获取验证码
				String phone = js.getString("phone").toString();
				if(XiaomanyaoInterface.checkusername(phone)==0){
					ackGetPhoneCode("0000",201,"账号已注册过");	//回应app
				}else{	
					String code = Common.CreateCode();
					SmsDemo.sendSms(phone, code);	//发送短信验证码
					CheckCode.CachePhoneCode(phone, code);	//保存手机号码和验证码到redis 服务器当中
					ackGetPhoneCode(code,200,"获取验证码成功");	//回应app
				}
			}else if(status.equals("checkcode")){	//校验验证码
				String phone = js.getString("phone");
				String code = js.getString("code");
				System.out.println("checkcode phone:"+phone+"--->code:"+code);
				int ret = CheckCode.checkPhoneCode(phone, code);
				System.out.println("checkPhoneCode ret = "+ret);
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
			}else{
				System.out.println("error status = "+status);
			}
		}else{
			System.out.println("error msgtype  = "+msgtype);
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
		HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),obj.toString());
	}
	private void ackCheckcode(int result,String resdata) throws IOException{
		ackRequest("phonecode", resdata, result);
	}
	private void AckRequestRegisterSuccess() throws IOException{
		ackRequest("register", "注册成功", 200);
	}
	private void AckRequestRegisterFailed() throws IOException{
		ackRequest("register", "用户已经注册过", 201);
	}
	private void AckRequestFailed() throws IOException{
		ackRequest("register", "无效的参数", 403);
	}
	private void ackRequest(String msgtype,String resdata,int result) throws IOException {
		String token =Common.GetToken();
		String jsondata =AckInterface.CreateAckJson(msgtype, resdata, token, result);
		System.out.println(jsondata);
		HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),jsondata);
	}
}

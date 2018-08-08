package com.lanbao.user;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.hibernate.db.HuserManger;
import com.lanbao.common.Common;
import com.lanbao.common.HttpServletUtils;
import com.lanbao.dbdata.CheckCode;
import com.lanbao.dbdata.XiaomanyaoInterface;
import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport {

	public String login() throws IOException{

		String boby=HttpServletUtils.getRequestBoby(ServletActionContext.getRequest());
		if(boby==null||boby.equals("")){
			System.out.println(" getRequestBoby failed ");
//			System.out.println("check checkusername ret:"+ret);
//			Logutils.e(ServletActionContext.getRequest(), "无效的参数");
//			CheckCode.checkPhoneCode("1218675043265","1234");
//			XiaomanyaoInterface.InsertData("测试", "test");
//			BackupMysql();
			AckRequestFailed(403,"无效的请求参数");
			return NONE;
		}
		UserReq req =(UserReq)JSONObject.toBean(JSONObject.fromObject(boby),UserReq.class);
		System.out.println(req.toString());
		if(req.getMsgtype()==null){
			AckRequestFailed(403,"无效的请求参数");
			return NONE;
		}
		if(req.getMsgtype().equals("login")){
			String usrname = req.getUsername();
			String passwd = req.getPasswd();
			if(usrname!=null&&passwd!=null){
				HuserManger usr =XiaomanyaoInterface.getUserMessageByusrname(usrname);
				if(usr==null){
					AckRequestFailed(201,"账号不存在");
				}else{
					if(usr.getPasswd().equals(passwd)){
						AckRequestLoginSuccess(usrname,usr.getDevSn());
					}else{
						AckRequestFailed(202,"密码错误");
					}
				}
			}
		}else if(req.getMsgtype().equals("autologin")){
			String usrname = req.getUsername();
			String token = req.getToken();
			if(usrname!=null&&token!=null){
				int ret =CheckCode.checkPhoneToken(usrname,token);
				switch (ret) {
				case CheckCode.ok_code:
					HuserManger usr =XiaomanyaoInterface.getUserMessageByusrname(usrname);
					if(usr!=null){
						ackCheckcode(usrname,usr.getDevSn(),200,"登录成功");
					}else{
						System.out.println("autologin: not found usrname");
						AckRequestFailed(201,"账号不存在");
					}
					break;
				case CheckCode.timeout_code:
					ackCheckcode(usrname,"",401,"请重新手动输入密码登录");
					break;
				case CheckCode.invalid_code:
					ackCheckcode(usrname,"",404,"无效登录参数，请重新手动输入密码登录");
					break;
				default:
					break;
				}
			}
		}
		
		return NONE;
	}
	
	private void AckRequestLoginSuccess(String usrname,String devSn) throws IOException{
		String token = Common.GetToken();
		System.out.println("start usrname:"+usrname+"--->CachePhoneToken:"+token);
		CheckCode.CachePhoneToken(usrname, token);
		System.out.println("ack login:"+usrname+"--->CachePhoneToken:"+token);
		String jsondata = AckInterface.CreateLoginAckJson("login", devSn, "登录成功",token, 200);
		HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),jsondata);
		
	}
	
	private void AckRequestFailed(int result,String resdata) throws IOException{
		String jsondata = AckInterface.CreateAckJson("login", resdata, "", result);
		HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),jsondata);
	}
	
	private void ackCheckcode(String usrname,String devSn,int result,String resdata) throws IOException{
		String token = Common.GetToken();
		CheckCode.CachePhoneToken(usrname, token);
		String jsondata = AckInterface.CreateLoginAckJson("autologin",devSn, resdata,token, result);
		System.out.println(jsondata);
		HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),jsondata);
	}
	
	private void BackupMysql(){
		XiaomanyaoInterface.InsertData("13427472484", "123456");
		XiaomanyaoInterface.InsertData("15919426331", "345542");
		XiaomanyaoInterface.InsertData("13423664018", "3390126");
		XiaomanyaoInterface.InsertData("18998391230", "787846060");
		XiaomanyaoInterface.InsertData("13560173493", "13560173493");
		XiaomanyaoInterface.InsertData("13688999416", "12345678");
		XiaomanyaoInterface.InsertData("13435424376", "123456"); 

		XiaomanyaoInterface.InsertData("18620423341", "123456"); 
		XiaomanyaoInterface.InsertData("13556824673", "123456"); 
		XiaomanyaoInterface.InsertData("18665552802", "123456"); 
	}
}

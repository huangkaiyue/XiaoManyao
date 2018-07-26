package com.lanbao.user;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.hibernate.db.HiberSql;
import com.hibernate.db.HuserManger;
import com.lanbao.common.Common;
import com.lanbao.dbdata.CheckCode;
import com.lanbao.dbdata.MysqlcallBack;
import com.lanbao.dbdata.XiaomanyaoInterface;
import com.lanbao.dbdata.XiaomanyaoUser;
import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport implements MysqlcallBack{

	public String login() throws IOException{
		System.out.println(" login ");
		
		String boby=getRequestBoby();
		if(boby==null||boby.equals("")){
			System.out.println(" getRequestBoby failed ");
//			int ret = mysqlxiaomanyao.checkusername("18814119548");
//			System.out.println("check checkusername ret:"+ret);
//			Logutils.e(ServletActionContext.getRequest(), "无效的参数");
//			CheckCode.checkPhoneCode("1218675043265","1234");
			XiaomanyaoInterface.InsertUsr("测试", "test");
			
			AckRequestFailed(403,"无效的请求参数");
			return NONE;
		}
		JSONObject js = JSONObject.fromObject(boby);
		String msgtype = js.getString("msgtype").toString();
		if(msgtype==null){
			AckRequestFailed(403,"无效的请求参数");
			return NONE;
		}
		if(msgtype.equals("login")){
			String usrname = js.getString("username").toString();
			String passwd = js.getString("passwd").toString();
			if(usrname!=null&&passwd!=null){
				
				int ret =XiaomanyaoInterface.checkusernameAndpasswd(usrname,passwd);
				switch (ret) {
				case 0:
					AckRequestLoginSuccess(usrname);
					break;
				case -1:
					AckRequestFailed(201,"账号不存在");
					break;
				case -2:
					AckRequestFailed(202,"密码错误");
					break;
				}
			}
		}else if(msgtype.equals("autologin")){
			String usrname = js.getString("username").toString();
			String token = js.getString("token").toString();
			if(usrname!=null&&token!=null){
				int ret =CheckCode.checkPhoneToken(usrname,token);
				switch (ret) {
				case CheckCode.ok_code:
					ackCheckcode(usrname,200,"登录成功");
					break;
				case CheckCode.timeout_code:
					ackCheckcode(usrname,401,"请重新手动输入密码登录");
					break;
				case CheckCode.invalid_code:
					ackCheckcode(usrname,404,"无效登录参数，请重新手动输入密码登录");
					break;
				default:
					break;
				}
			}
		}
		
		return NONE;
	}
	
	private void AckRequestLoginSuccess(String usrname) throws IOException{
		String token = Common.GetToken();
		CheckCode.CachePhoneToken(usrname, token);
		JSONObject obj = new JSONObject();
		obj.put("msgtype", "login");
		obj.put("token", token);
		obj.put("resdata", "登录成功");
		obj.put("result", 200);
		AckRequestResponse(obj.toString());
		
	}
	
	private void AckRequestFailed(int result,String resdata) throws IOException{
		JSONObject obj = new JSONObject();
		obj.put("msgtype", "login");
		obj.put("resdata", resdata);
		obj.put("result", result);
		AckRequestResponse(obj.toString());
	}
	
	private void ackCheckcode(String usrname,int result,String resdata) throws IOException{
		JSONObject obj = new JSONObject();
		obj.put("msgtype", "autologin");
		String token = Common.GetToken();
		obj.put("token", token);
		obj.put("resdata", resdata);
		obj.put("result", result);
		CheckCode.CachePhoneToken(usrname, token);
		System.out.println(obj.toString());
		AckRequestResponse(obj.toString());
	}
	
	private void AckRequestResponse(String response) throws IOException{
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

	@Override
	public void GetSqlData(JSONArray jarr, String usrname, String passwd,
			String date) {
		// TODO Auto-generated method stub
		
	}
}

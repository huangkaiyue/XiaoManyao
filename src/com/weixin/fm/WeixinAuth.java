package com.weixin.fm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lanbao.common.HttpServletUtils;
import com.lanbao.user.AckInterface;
import com.opensymphony.xwork2.ActionSupport;
import com.server.sdk.MsgInterface;

public class WeixinAuth extends ActionSupport{
	public String weixinAuthInter() throws IOException{
		String boby=HttpServletUtils.getRequestBoby(ServletActionContext.getRequest());
		if(boby==null||boby.equals("")){
			System.out.println("WeixinAuth request failed");
			AckRequest(403,"无效的请求参数");
			return NONE;
		}
		System.out.println("WeixinAuth recv:"+boby);
		JSONObject js = JSONObject.fromObject(boby);
		String msgtype= js.get("msgtype").toString();
		if(msgtype.equals("getunionId")){
			String usercode = js.getString("usercode").toString();
			String userencryData= js.getString("userencryData").toString();
			String openid=GetWeixinOpenid(usercode);
			Ackopenid(openid);
		}else if(msgtype.equals("bindsn")){
			String unionId = js.getString("unionId").toString();
			String devsn= js.getString("devsn").toString();
			int ret =WxSqlInterface.WxSqlBindDevSn(unionId, devsn);
			if(ret==0){
				AckBindDevSn(unionId,devsn,200,"bind ok");
			}else if(ret==-2){
				AckBindDevSn(unionId,devsn,201,"已经绑定过设备");
			}
			else{
				AckBindDevSn(unionId,devsn,404,"not found unionId");
			}
		}else if(msgtype.equals("scandev")){
			String unionId = js.getString("unionId").toString();
			String jsondata=WxSqlInterface.CreateWxuserDevsnByUnionId_AckJson(unionId);
			HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),jsondata);
		}else if(msgtype.equals("pushMsg")){
			String msgdir = js.getString("dir").toString();
			String unionId = js.getString("unionId").toString();
			String devsn = js.getString("devsn").toString();
			String formId = js.getString("formId").toString();
			JSONArray jarr = js.getJSONArray("msgbody");
			String type="";
			String url="";
			for(int i=0;i<jarr.size();i++){
				JSONObject jobj= (JSONObject) jarr.get(i);
				type= jobj.getString("type").toString();
				url= jobj.getString("url").toString();
			}
			System.out.println("type:"+type+"--->url:"+url);
			MsgInterface.WxMsgSend( devsn, type, formId, url);
			
			String jsondata = AckInterface.CreateAckJson("pushMsg", "推送成功", "", 200);
			HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),jsondata);
		}else if(msgtype.equals("delete")){
			String unionId = js.getString("unionId").toString();
			String devsn = js.getString("devsn").toString();
			WxSqlInterface.deleteDevSnByUnionId(unionId, devsn);
			String jsondata = AckInterface.CreateAckJson("delete", "删除成功", "", 200);
			HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),jsondata);
		}
		return NONE;
	}
	private void AckRequest(int result,String resdata) throws IOException{
		String jsondata = AckInterface.CreateAckJson("getunionId", resdata, "", result);
		HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),jsondata);
	}
	private void Ackopenid(String openid) throws IOException{
		String jsondata ="";
		JSONObject json = new JSONObject();
		json.put("msgtype", "getunionId");
		json.put("unionId", openid);
		json.put("openid", openid);
		jsondata = json.toString();
		if(WxSqlInterface.checkUnionId(openid)==false){
			WxSqlInterface.InsertWxunionId(openid, openid);
		}else{
			System.out.println("openid:"+openid+"--->"+"is insert weixin usertable");
		}
		HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),jsondata);
	}
	private void AckBindDevSn(String unionId,String devsn,int result,String resdata) throws IOException{
		String jsondata ="";
		JSONObject json = new JSONObject();
		json.put("msgtype", "bindsn");
		json.put("unionId", unionId);
		json.put("devsn", devsn);
		json.put("result", result);
		json.put("resdata", resdata);
		jsondata = json.toString();
		System.out.println("AckBindDevSn:"+jsondata);
		HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),jsondata);
	}
	private static String GetWeixinOpenid(String js_code){
        String openid = "";
        String line;
        StringBuffer sb=new StringBuffer();
        BufferedReader in = null;
        String appid = "wx17cd960849931531";
        String secret="e908f68312b0d79636fc1ac102550244";
    
		try {
			URL realUrl = new URL("https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+js_code+"&grant_type=authorization_code");
			try {
				URLConnection conn = realUrl.openConnection();
	            conn.setRequestProperty("contentType", "utf-8"); 
	            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
	            //设置超时时间
	            conn.setConnectTimeout(3000);
	            conn.setReadTimeout(5000);
	            // 建立实际的连接
	            conn.connect();
	            // 定义 BufferedReader输入流来读取URL的响应,设置接收格式
	            in = new BufferedReader(new InputStreamReader(
	                    conn.getInputStream(),"utf-8"));
	            while ((line = in.readLine()) != null) {
	                sb.append(line);
	            }
	            String server_result=sb.toString();
	            
				if(server_result.contains("openid")){
					JSONObject js = JSONObject.fromObject(server_result);
					openid =js.getString("openid");
				}else{
					openid="";
				}
	            
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//{"session_key":"oU94TH7xyH3dB9S4jUJJGg==","openid":"oXuHx5FURQodw9XRMim76BB7Pquk"}
		return openid;
	}
}

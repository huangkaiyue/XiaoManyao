package com.lanbao;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;



public class HelloAction extends ActionSupport{
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
//		String Parameter = ServletActionContext.getRequest().getParameter("method");
		String Parameter = ServletActionContext.getRequest().getParameter("FROM");
		System.out.println("ActionSupport Method :"+ServletActionContext.getRequest().getMethod()+"--->"+"getParameter:"+Parameter);
		
		if(Parameter.equals("app")){
			String json = "server";   
			String boby=getRequestBoby();
			if(boby==null){
				return "failed";
			}
			
			JSONObject js = JSONObject.fromObject(boby);
			String msgtype = js.getString("msgtype").toString();
			if(msgtype==null){
				return "failed";
			}
			if(msgtype.equals("scan")){		//�ο��ļ��б�
				System.out.println("is app scan list ...");
				json =getMysqlMusicList(js.getString("page").toString());
			}else if(msgtype.equals("geturl")){	//��ȡ�������ӵ�ַ	
				
				String ossName =  js.getString("ossName").toString();
				System.out.println("get oss name:"+ossName);
				AliyunOss obj = new AliyunOss();
				String ossJson= obj.GetOssUrlByName(ossName);
				json=ossJson;
				System.out.println("ossJson:"+json);
			}
			HttpServletResponse Response= ServletActionContext.getResponse();
			Response.setHeader("Content-type", "text/html;charset=UTF-8");  
			Response.getWriter().print(json);
			Response.getWriter().flush();
			Response.getWriter().close();
//			ServletActionContext.getServletContext().getRequestDispatcher("/musiclist.jsp").forward(request, ServletActionContext.getResponse());
		}else if(Parameter.equals("phone")){
			System.out.println("recv app request :");
			HttpServletResponse Response= ServletActionContext.getResponse();
			Response.setHeader("Content-type", "text/html;charset=UTF-8");  
			Response.getWriter().print("test data");
			Response.getWriter().flush();
			Response.getWriter().close();
		}
		return "success";
	}
	
	private String getRequestBoby(){
		HttpServletRequest request =ServletActionContext.getRequest();
		int len = request.getContentLength();
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
	private String getMysqlMusicList(String page){
		String list="";
		JSONObject js = new JSONObject();
		js.put("msgtype", "scan");
		JSONArray arr = new JSONArray();
	
		
		JSONObject data1 = new JSONObject(); 
		data1.put("name", "想起.mp3");
		data1.put("ossname", "lanbao");
		data1.put("md5", "xxx");
		arr.add(data1);
		
		JSONObject data2 = new JSONObject(); 
		data2.put("name", "告白气球.mp3");
		data2.put("ossname", "lanbao");
		data2.put("md5", "xxx");
		arr.add(data2);
		
		
		JSONObject data3 = new JSONObject(); 
		data3.put("name", "后来.mp3");
		data3.put("ossname", "lanbao1");
		data3.put("md5", "xxx");
		arr.add(data3);
		
		js.put("list", arr);
		list = js.toString();
		System.out.println(list);
		return list;
	}	
}
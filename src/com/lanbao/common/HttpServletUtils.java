package com.lanbao.common;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpServletUtils {

	public static void AckRequestResponse(HttpServletResponse Response, String response) throws IOException{
		Response.setHeader("Content-type", "text/html;charset=UTF-8");  
		Response.getWriter().print(response);
		Response.getWriter().flush();
		Response.getWriter().close();
	}
	public static String getRequestBoby(HttpServletRequest request){
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
//			System.out.println("getRequestBoby: get len="+len+" boby is :"+str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}

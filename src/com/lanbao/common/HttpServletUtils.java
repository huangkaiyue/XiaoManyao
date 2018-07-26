package com.lanbao.common;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class HttpServletUtils {

	public static void AckRequestResponse(HttpServletResponse Response, String response) throws IOException{
		Response.setHeader("Content-type", "text/html;charset=UTF-8");  
		Response.getWriter().print(response);
		Response.getWriter().flush();
		Response.getWriter().close();
	}
}

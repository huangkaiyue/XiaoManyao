package com.start.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.lanbao.dbdata.XiaomanyaoUser;
import com.xiaomanyao.music.LoadMusic;

public class InitFilter implements Filter{
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("================>[Filter]start create database and table  version v.0.0.2");  
		
		String logPath = arg0.getServletContext().getRealPath("/log");
		ConfigServer.getInstance().setLogPath(logPath);
		System.out.println("init path :"+ConfigServer.getInstance().getLogPath());
		XiaomanyaoUser obj = new XiaomanyaoUser(null);
//		obj.CreateDataBase(XiaomanyaoUser.databaseName);
//		obj.CreateTable(XiaomanyaoUser.tableName);
		LoadMusic.loadmusicfromMysql(arg0.getServletContext().getRealPath("/XiaomanyaoFile")+"/albumlist.json");
		System.out.println("================>[Filter]start create database and table ok");  
		
	}

}

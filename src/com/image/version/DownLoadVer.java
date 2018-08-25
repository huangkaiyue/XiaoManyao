package com.image.version;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.hibernate.db.HibernateUtil;
import com.hibernate.db.Hversion;
import com.lanbao.common.FileInter;
import com.lanbao.common.HttpServletUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.start.server.ConfigServer;

public class DownLoadVer extends ActionSupport{
	String reslut =SUCCESS;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		String method = ServletActionContext.getRequest().getParameter("method");		
		if(method.equals("down")){
			String filepathName = ConfigServer.getInstance().getVersionDir()+ServletActionContext.getRequest().getParameter("fileName");
			
			FileInter.downloadFile(ServletActionContext.getResponse(),ServletActionContext.getServletContext(),filepathName,ServletActionContext.getRequest().getParameter("fileName"));
			reslut=NONE;
		}else if(method.equals("checkVersion")){
			Hversion hver = XmyVerSql.ScanVersion();
			JSONObject obj = new JSONObject();
			obj.put("version", hver.getName());
			String downUrl = ConfigServer.getInstance().getVersionUrl()+ConfigServer.getInstance().getVersionDir();
			obj.put("url",downUrl+hver.getName());
			String downUrls = ConfigServer.getInstance().getHttsVersionUrl()+ConfigServer.getInstance().getVersionDir();
			obj.put("urls",downUrls+hver.getName());
			obj.put("verNum", hver.getMessage());
			obj.put("md5",hver.getMd5());
			obj.put("message", hver.getMessage());
			HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(), obj.toString());
			reslut=NONE;
		}
		return reslut;
	}
}

package com.image.version;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.hibernate.db.Hversion;
import com.lanbao.common.FileInter;
import com.lanbao.common.HttpServletUtils;
import com.opensymphony.xwork2.ActionSupport;

public class DownLoadVer extends ActionSupport{
	String reslut =SUCCESS;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		String method = ServletActionContext.getRequest().getParameter("method");		
		if(method.equals("down")){
			String fileName = "xiaomanyaoImageVesion/"+ServletActionContext.getRequest().getParameter("fileName");
			
			FileInter.downloadFile(ServletActionContext.getResponse(),ServletActionContext.getServletContext(),fileName);
			reslut=NONE;
		}else if(method.equals("checkVersion")){
			Hversion hver = XmyVerSql.ScanVersion();
			JSONObject obj = new JSONObject();
			obj.put("version", hver.getName());
			obj.put("id", hver.getuId());
			obj.put("md5",hver.getMd5());
			obj.put("message", hver.getMessage());
			HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(), obj.toString());
			reslut=NONE;
		}
		return reslut;
	}
}

package com.xiaomanyao.music;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.hibernate.db.HibernateUtil;
import com.lanbao.common.Common;
import com.lanbao.common.FileInter;
import com.lanbao.common.HttpServletUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.start.server.ConfigServer;

public class MusicList extends ActionSupport{
	String reslut =SUCCESS;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		String method = ServletActionContext.getRequest().getParameter("method");		
		System.out.println("ActionSupport Method :"+ServletActionContext.getRequest().getMethod()+"--->"+"getParameter:"+method);
		if(method.equals("down")){
			HttpServletRequest request =ServletActionContext.getRequest();
			request.setCharacterEncoding("UTF-8");
			ServletActionContext.setRequest(request);
			String fileName = request.getParameter("fileName");
			FileInter.downloadFile(ServletActionContext.getResponse(),ServletActionContext.getServletContext(),fileName,fileName);
			reslut=NONE;	
		}else if(method.equals("Album")){
			String path = ConfigServer.getInstance().getCacheDir()+"/albumlist.json"; 
			String json = CreateMusicListJson(path);
			HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),json);
			reslut=NONE;		
		}else if(method.equals("Albumlist")){
			String indexStr = ServletActionContext.getRequest().getParameter("index");
			if(indexStr==null||indexStr.equals("")){
				AckRequestStatus(404,"invalid index");
				reslut=NONE;
			}
			int index = Integer.parseInt(indexStr);
			System.out.println("get album index:"+index);
			String json =XiaomanMusicAppInterface.ScanAlbumMusicList(index);
			if(json.equals("")){
				AckRequestStatus(404,"invalid index");
			}else{
				HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),json);
			}
			reslut=NONE;
		}else if(method.equals("moreAlbum")){
			String pageStr = ServletActionContext.getRequest().getParameter("page");	
			int page = Integer.parseInt(pageStr);
			String json="";
//			if(page>=2){
//				json =XiaomanMusicAppInterface.loadMoreAlbum(page);
//				reslut=NONE;
//			}else{
//		        String path = ConfigServer.getInstance().getCacheDir()+"/albumlist.json"; 
//				json = CreateMusicListJson(path);	
//			}
			System.out.println("page:"+page);
			json = XiaomanMusicAppInterface.ScanAlbumByPage(page);
			HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),json);
			reslut=NONE;
		}
		return reslut;
	}
	
	private String CreateMusicListJson(String fileName){	//获取文件列表名字和下载链接地址
		   return Common.readFile(fileName);
	}
		
	private void AckRequestStatus(int code,String resdata){
		JSONObject obj = new JSONObject();
		obj.put("msgtype", "album");
		obj.put("resdata", resdata);
		obj.put("result", code);
		try {
			HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),obj.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

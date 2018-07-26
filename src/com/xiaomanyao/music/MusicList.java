package com.xiaomanyao.music;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.hibernate.db.HibernateUtil;
import com.lanbao.ConsumerDemo;
import com.lanbao.common.Common;
import com.lanbao.common.FileInter;
import com.lanbao.common.HttpServletUtils;
import com.opensymphony.xwork2.ActionSupport;

public class MusicList extends ActionSupport{
	String reslut =SUCCESS;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		String method = ServletActionContext.getRequest().getParameter("method");		
		System.out.println("ActionSupport Method :"+ServletActionContext.getRequest().getMethod()+"--->"+"getParameter:"+method);
		if(method.equals("down")){
			String fileName = ServletActionContext.getRequest().getParameter("fileName");
			FileInter.downloadFile(ServletActionContext.getResponse(),ServletActionContext.getServletContext(),fileName);
			reslut=NONE;	
		}else if(method.equals("Album")){
			String path = ServletActionContext.getServletContext().getRealPath("/XiaomanyaoFile/albumlist.json");
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
			String downUrl = HibernateUtil.getConfiguration().getProperties().getProperty("hibernate.connection.downurl");
			String json =XiaomanMusicAppInterface.ScanAlbumMusicList(index,downUrl);
			if(json.equals("")){
				AckRequestStatus(404,"invalid index");
			}else{
				HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),json);
			}
			reslut=NONE;
		}
		else if(method.equals("devDownload")){
			String devicesName = "";
			String json =downLoadRequest("","");
			//将下载请求推送给设备端
			int ret = ConsumerDemo.SendMessage(devicesName, json);
			if(ret==0){
				HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),json);
			}else{
				
			}
			reslut=NONE;	
		}
		return reslut;
	}
	
//	private void downloadFile()throws Exception{	//下载文件接口 
//		String fileName = ServletActionContext.getRequest().getParameter("fileName");
//		
//		try {
//			fileName = new String(fileName.getBytes("ISO8859-1"),"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String basePath = ServletActionContext.getServletContext().getRealPath("/XiaomanyaoFile");
//		InputStream in = new FileInputStream(new File(basePath,fileName));
//		fileName = URLEncoder.encode(fileName, "UTF-8");
//		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;fileName=" + fileName);
//		OutputStream out = ServletActionContext.getResponse().getOutputStream();
//		
//		byte[] b = new byte[1024];
//		int len = -1;
//		while ((len = in.read(b)) != -1){
//			out.write(b, 0, len);
//		}
//		out.close();
//		in.close();
//		System.out.println("fileName send file ok:"+fileName+"--->return:NONE");
//	} 
	
	private String CreateMusicListJson(String fileName){	//获取文件列表名字和下载链接地址
//		System.out.println("path:"+path);
		   return Common.readFile(fileName);
	}
	
	private String downLoadRequest(String filename,String downUrl){	//处理下载文件请求
		String basePath = ServletActionContext.getServletContext().getRealPath("/XiaomanyaoFile");
		String md5 ="";
		try {
			md5 = FileInter.getMd5ByFile(basePath+"/"+filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.put("url", "");
		json.put("filename", "");
		json.put("md5", md5);
		return json.toString();
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

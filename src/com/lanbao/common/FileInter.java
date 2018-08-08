package com.lanbao.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

public class FileInter {

	 private static  void getFile(ArrayList<String> arr,String path){
		 File rootDir = new File(path);
		 if(!rootDir.isDirectory()){
//			 System.out.println("文件名"+rootDir.getAbsolutePath());
//			 arr.add(rootDir.getAbsolutePath());
//			 try {
//				System.out.println(getMd5ByFile(rootDir.getAbsolutePath()));
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			 arr.add(rootDir.getName());
		 }else{
			 String[] fileList =  rootDir.list();
			 for (int i = 0; i < fileList.length; i++) {
				 path = rootDir.getAbsolutePath()+"\\"+fileList[i];
				 getFile(arr,path);      
		      } 
		   }    

		  }
	 public static  ArrayList<String> getDirFile(String path){
		 ArrayList<String> arr = new ArrayList<String>();
		 getFile(arr,path);
		 return arr;
	 }
	 private static String downUrl = "http://localhost:9000/XiaoManyao/music?method=down&fileName=";
	 public static void CreateMusicListJson(String path){
		System.out.println("path:"+path);
		ArrayList<String> arr =FileInter.getDirFile(path);
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(int i=0;i<arr.size();i++){
			System.out.println(arr.get(i).toString());
			JSONObject data = new JSONObject();
			data.put("url", downUrl+arr.get(i).toString());
			data.put("filename", arr.get(i).toString());
			jarr.add(data);
			}
			json.put("mulist", jarr); 
			System.out.println("CreateMusicListJson:"+json.toString());
		}
	 
	 
	 public static String getMd5ByFile(String pathname) throws FileNotFoundException {
		 File file = new File(pathname);
		 String value = null;
		 FileInputStream in = new FileInputStream(file);
		 try {
			 	MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		    	MessageDigest md5 = MessageDigest.getInstance("MD5");
		    	md5.update(byteBuffer);
		    	BigInteger bi = new BigInteger(1, md5.digest());
		    	value = bi.toString(16);
		 	} catch (Exception e) {
		 		e.printStackTrace();
		 	} finally {
	            if(null != in) {
		            try {
		            	in.close();
		            } catch (IOException e) {
			        	e.printStackTrace();
			    	}
	            }
		 	}
		 return value;
	   }
	 
	 public static void downloadFile(HttpServletResponse  response,ServletContext context,String fileName)throws Exception{	//下载文件接口 
			try {
				fileName = new String(fileName.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String basePath = context.getRealPath("/XiaomanyaoFile");
			File file =new File(basePath,fileName);
			InputStream in = new FileInputStream(file);
			
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.setContentLength((int)file.length());
			if(fileName.contains("mp3")){	//音频流传输
				response.setHeader("Content-Type", "audio/mpeg");
				System.out.println("set play mp3...............request:"+fileName);
			}else{
				//设置传输以文件方式
				response.setHeader("content-disposition", "attachment;fileName=" + fileName);
				response.setContentType("application/octet-stream");
			}
			OutputStream out = ServletActionContext.getResponse().getOutputStream();
			
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = in.read(b)) != -1){
				out.write(b, 0, len);
			}
			out.close();
			in.close();
			System.out.println("fileName send file ok:"+fileName+"--->return:NONE");
		} 
	 
	 public static void main(String[] args) {
		   // TODO Auto-generated method stub
		   String path ="D:\\work0719\\Server\\aliyunServer\\CopyofLanbaofactory\\src\\com\\lanbao\\factory";
		   CreateMusicListJson(path);
		 }
}

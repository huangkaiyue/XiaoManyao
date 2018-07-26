package com.lanbao.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class Common {
	private String accessId="LTAIdpIiWtBnqtuA";
	private String accessKey="3x4rOYN4RcjYKsLvXgSxeQvegqeIjx";
	private String accountEndpoint="https://1226525498732712.mns.cn-hangzhou.aliyuncs.com/";
	
		
	
//	private String accessId="LTAI0V1E2e1MAHdV";
//	private String accessKey="U87P0Vy0H9IXdxgb1DdBVoya89aK4r";
	
	
	public String getAccessId(){
		return accessId;
	}
	public String getAccessKey(){
		return accessKey;
	}
	public String getAccountEndpoint(){
		return accountEndpoint;
	}
	public static String GetToken(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	public static String CreateCode(){
		String str="0123456789";
		StringBuilder sb=new StringBuilder(4);
		for(int i=0;i<4;i++)
		{
			char ch=str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);
		}
		String num =sb.toString();
		System.out.println("create number:"+num);
		return num;
	}
	
	public static String createDirBydate(String basePath){
		String dirName = "";
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);  
		int month = c.get(Calendar.MONTH);   
		int date = c.get(Calendar.DATE);    
//		int hour = c.get(Calendar.HOUR_OF_DAY);   
//		int minute = c.get(Calendar.MINUTE);   
//		int second = c.get(Calendar.SECOND);    
		
		dirName = year+"-"+month+"-"+date;
		File dir = new File(basePath+"/"+dirName);
		if(dir.exists()){
			System.out.println("dir is exists");
		}else{
			System.out.println("dir not exists");
			dir.mkdir();
		}		
		System.out.println("dirName:"+dirName);
		return dirName;
	}
    public static String readFile(String fileName){
        BufferedReader br;
        String str ="";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8"));
	        String line = null; 
	        
	        try {
				while ((line = br.readLine()) != null) {
					str +=line;
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return str;
    }
	public static void WriteStrToFile(String filename,String str){
		try {
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File(filename)), "UTF-8"); 
			osw.write(str);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

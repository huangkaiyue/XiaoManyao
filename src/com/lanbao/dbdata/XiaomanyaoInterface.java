package com.lanbao.dbdata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.hibernate.db.HiberSql;
import com.hibernate.db.HuserManger;
import com.lanbao.common.Logutils;

public class XiaomanyaoInterface {

	public static int InsertUsr(String username,String passwd){
		int ret =-1;
		HuserManger usr = new HuserManger();
		usr.setUsrname(username);
		usr.setPasswd(passwd);
		usr.setChmod("root");
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String date =df.format(day);
		usr.setDate(date);
		try {
			usr.toString();
			HiberSql.Insert(usr);
			ret=0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logutils.e("insert data failed");
		}
		return ret;
	}
	public static int checkusernameAndpasswd(String usrname,String passwd){
		int ret =-1;
		List<Object> list = HiberSql.ScanUser(usrname);
		 if(list!=null&&list.size()!=0){
			 Iterator iterator = list.iterator();
			 while(iterator.hasNext()){
				 HuserManger s = (HuserManger) iterator.next();
				 if(s.getPasswd().equals(passwd)){
					 ret=0;
				 }else{
					 ret=-2;
				 }
				 Logutils.e(s.toString());  		
			 }
	       }else{
	    	   Logutils.e("not find:"+usrname);     
	       }
		return ret;
	}
	public static int checkusername(String usrname){
		int ret =-1;
		List<Object> list = HiberSql.ScanUser(usrname);
		 if(list!=null&&list.size()!=0){
			 Iterator iterator = list.iterator();
			 while(iterator.hasNext()){
				 HuserManger s = (HuserManger) iterator.next();
				 ret=0;
				 Logutils.e(s.toString());  		
			 }
	       }else{
	    	   Logutils.e("not find:"+usrname);     
	       }
		return ret;
	}
	public static int updatepasswd(String usrname,String newpasswd){
		int ret =0;
		HiberSql.updateUsrPasswd(usrname, newpasswd);
		return ret;
	}
}

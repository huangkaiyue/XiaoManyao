package com.lanbao.dbdata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.db.HiberSql;
import com.hibernate.db.HibernateUtil;
import com.hibernate.db.HuserManger;
import com.hibernate.db.Weixinuser;
import com.hibernate.db.WxDevsnlistUtil;
import com.lanbao.common.Logutils;

public class XiaomanyaoInterface {

	public static int InsertData(String username,String passwd){
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
	
	public static HuserManger getUserMessageByusrname(String usrname){
		HuserManger usr=null;
		List<Object> list = HiberSql.ScanUser(usrname);
		 if(list!=null&&list.size()!=0){
			 Iterator iterator = list.iterator();
			 while(iterator.hasNext()){
				 usr = (HuserManger) iterator.next();
				 Logutils.e(usr.toString());  		
			 }
	       }else{
	    	   Logutils.e("not find:"+usrname);     
	       }
		return usr;
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
	public static  void updatedevSn(String usrname,String devSn){
		HiberSql.updateUsrdevSn(usrname, devSn);
	}
	
	public static int UserSqlBindDevSn(String phone,String devsn){
	int ret=-1;
//
//	HuserManger user=null;
//	Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
//	Transaction tx = session.beginTransaction();// 开启事务
//	String sql = "from HuserManger usr where usr.usrname=?";
//	Query query =session.createQuery(sql);//HQL创建查询语句
//	query.setParameter(0,phone);
//	int uId= 0;
//	List<Object>list=query.list();
//	if(list!=null&&list.size()!=0){
//		Iterator iterator = list.iterator();
//		while(iterator.hasNext()){
//			user = (HuserManger) iterator.next();
//			System.out.println("HuserManger:"+user.toString()); 
//			uId=user.getuId();
//			ret =0;
//		}	 
//	}
//	
//	sql = "from WxDevsnlistUtil wl where wl.devsn=?";
//	query =session.createQuery(sql);//HQL创建查询语句
//	query.setParameter(0,devsn);
//	list=query.list();
//	WxDevsnlistUtil wutils = null;
//	boolean bind_ok=false;
//	if(list!=null&&list.size()!=0){
//		Iterator iterator = list.iterator();
//		while(iterator.hasNext()){
//			wutils = (WxDevsnlistUtil) iterator.next();
//			Weixinuser wt = wutils.getDevsn_id();
//			System.out.println("WxSqlBindDevSn:"+wutils.toString()+"--->wt.getuId():"+wt.getuId()+"--->wUser.getuId():"+wUser.getuId()); 
//			if(wt.getuId()==wUser.getuId()){
//				System.out.println("is bind already:"+wutils.getDevsn()); 
//				bind_ok = true;
//				ret =-2;
//			}else{
//				System.out.println("not bind devsn:"+devsn); 
//			}
//		}	 
//	}		
//	if(bind_ok==false){
//		WxDevsnlistUtil wxlist = new WxDevsnlistUtil();
//		wxlist.setDevsn(devsn);
//		Date day=new Date();    
//		wxlist.setDate(day);
//		wxlist.setDevsn_id(wUser);
//		session.save(wxlist);
//		ret =0;
//		System.out.println("phone:"+phone+"--->bind devsn"+devsn); 
//	}
//	tx.commit();// 提交事务
//	HibernateUtil.closeSession();// 关闭
	return ret;
}

	
}

package com.lanbao.dbdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.db.HiberSql;
import com.hibernate.db.HibernateUtil;
import com.hibernate.db.HuserDevsnlistUtil;
import com.hibernate.db.HuserManger;
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
		HuserManger user=null;
		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务
		String sql = "from HuserManger usr where usr.usrname=?";
		Query query =session.createQuery(sql);//HQL创建查询语句
		query.setParameter(0,phone);
		int uId= 0;
		List<Object>list=query.list();
		if(list!=null&&list.size()!=0){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				user = (HuserManger) iterator.next();
				System.out.println("UserSqlBindDevSn :"+user.toString()); 
				uId=user.getuId();
				user = (HuserManger) iterator.next();
				if(user.getDevSn().equals(devsn)){
//					tx.commit();// 提交事务
//					HibernateUtil.closeSession();// 关闭
//					return 0;
				}else{
					user.setDevSn(devsn);
				}
			}	 
		}else{
			return -1;
		}
		sql = "from HuserDevsnlistUtil husr where husr.devsn=?";
		query =session.createQuery(sql);//HQL创建查询语句
		query.setParameter(0,devsn);
		list=query.list();
		HuserDevsnlistUtil husrDev = null;
		boolean bind_ok=false;
		if(list!=null&&list.size()!=0){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				husrDev = (HuserDevsnlistUtil) iterator.next();
				HuserManger husr = husrDev.getDevsn_id();
				System.out.println("HuserDevsnlistUtil:"+husrDev.toString()+"--->HuserManger:"+husr.toString()); 
				if(husrDev.getuId()==husr.getuId()){
					System.out.println("is bind already:"+husrDev.getDevsn()); 
					bind_ok = true;
					ret =-2;
				}else{
					System.out.println("not bind devsn:"+devsn); 
				}
			}	 
		}		
		if(bind_ok==false){
			HuserDevsnlistUtil wDev = new HuserDevsnlistUtil();
			wDev.setDevsn(devsn);
			Date day=new Date();    
			wDev.setDate(day);
			wDev.setDevsn_id(user);
			session.save(wDev);
			ret =0;
			System.out.println("phone:"+phone+"--->bind devsn"+devsn); 
		}
		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭
		return ret;
	}
	
	public static Set<HuserDevsnlistUtil> ScanuserDevsnByPhoneId(String phone){
		int ret =-1;
		String str ="";
		HuserManger wxUser =null; 

		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务
		String sql = "from HuserManger al where al.usrname=?";
		Query query =session.createQuery(sql);//HQL创建查询语句
		query.setParameter(0,phone);
		List<Object>list=query.list();
		if(list!=null&&list.size()!=0){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				wxUser = (HuserManger) iterator.next();
				System.out.println("ScanuserDevsnByPhoneId:"+wxUser.toString()); 
			}	 
		}
		Set<HuserDevsnlistUtil> devlist=wxUser.getDevsnS();
		Iterator iterator = devlist.iterator();
		while(iterator.hasNext()){
			WxDevsnlistUtil s= (WxDevsnlistUtil) iterator.next();
			System.out.println("ScanuserDevsnByPhoneId :"+s.getDevsn());
		}
		HuserDevsnlistUtil headItem = new HuserDevsnlistUtil();	//当前手机条目绑定的SN号
		headItem.setDevsn(wxUser.getDevSn());
		headItem.setuId(wxUser.getuId());
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date;
		try {
			date = simpleDateFormat.parse(wxUser.getDate());
			headItem.setDate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭
		devlist.add(headItem);
		return devlist;
	}
}

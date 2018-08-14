package com.weixin.fm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.db.HiberSql;
import com.hibernate.db.HibernateUtil;
import com.hibernate.db.Weixinuser;
import com.hibernate.db.WxDevsnlistUtil;
import com.lanbao.common.Logutils;

public class WxSqlInterface {
	
	public static boolean checkUnionId(String unionId){
		Weixinuser wUser=null;
		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务
		String sql = "from Weixinuser wx where wx.unionId=?";
		Query query =session.createQuery(sql);//HQL创建查询语句
		query.setParameter(0,unionId);
		
		List<Object>list=query.list();
		if(list!=null&&list.size()!=0){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				wUser = (Weixinuser) iterator.next();
				System.out.println("checkUnionId:"+wUser.toString()); 
				tx.commit();// 提交事务
				HibernateUtil.closeSession();// 关闭
				return  true;
			}	 
	
		}
		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭
		return  false;
	}
	
	public static int InsertWxunionId(String unionId,String openid){
		int ret =-1;
		Weixinuser usr = new Weixinuser();
		usr.setUnionId(unionId);
		usr.setOpenid(openid);
		usr.setChmod("root");
		Date day=new Date();    
		usr.setDate(day);
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
	
	public static int WxSqlBindDevSn(String unionId,String devsn){
		int ret=-1;

		Weixinuser wUser=null;
		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务
		String sql = "from Weixinuser wx where wx.unionId=?";
		Query query =session.createQuery(sql);//HQL创建查询语句
		query.setParameter(0,unionId);
		int uId= 0;
		List<Object>list=query.list();
		if(list!=null&&list.size()!=0){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				wUser = (Weixinuser) iterator.next();
				System.out.println("WxSqlBindDevSn:"+wUser.toString()); 
				uId=wUser.getuId();
				ret =0;
			}	 
		}
		
		sql = "from WxDevsnlistUtil wl where wl.devsn=?";
		query =session.createQuery(sql);//HQL创建查询语句
		query.setParameter(0,devsn);
		list=query.list();
		WxDevsnlistUtil wutils = null;
		boolean bind_ok=false;
		if(list!=null&&list.size()!=0){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				wutils = (WxDevsnlistUtil) iterator.next();
				System.out.println("WxSqlBindDevSn:"+wutils.toString()); 
				if(wutils.getuId()==wUser.getuId()){
					System.out.println("is bind already:"+wutils.getDevsn()); 
					bind_ok = true;
					ret =-2;
				}else{
					System.out.println("not bind devsn:"+devsn); 
				}
			}	 
		}		
		if(bind_ok==false){
			WxDevsnlistUtil wxlist = new WxDevsnlistUtil();
			wxlist.setDevsn(devsn);
			Date day=new Date();    
			wxlist.setDate(day);
			wxlist.setDevsn_id(wUser);
			session.save(wxlist);
			ret =0;
			System.out.println("unionId:"+unionId+"--->bind devsn"+devsn); 
		}
		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭
		return ret;
	}
	public static Set<WxDevsnlistUtil> ScanWxuserDevsnByUnionId(String unionId){
		int ret =-1;
		String str ="";
		Weixinuser wxUser =null; 

		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务
		String sql = "from Weixinuser al where al.unionId=?";
		Query query =session.createQuery(sql);//HQL创建查询语句
		query.setParameter(0,unionId);
		List<Object>list=query.list();
		if(list!=null&&list.size()!=0){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				wxUser = (Weixinuser) iterator.next();
				System.out.println("ScanWxuserDevsnByUnionId:"+wxUser.toString()); 
			}	 
		}
		Set<WxDevsnlistUtil> wxdevlist=wxUser.getDevsnS();
		Iterator iterator = wxdevlist.iterator();
		while(iterator.hasNext()){
			WxDevsnlistUtil s= (WxDevsnlistUtil) iterator.next();
			System.out.println("ScanWxuserDevsnByUnionId :"+s.getDevsn());
		}
		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭
		return wxdevlist;
	}
	
	public static String CreateWxuserDevsnByUnionId_AckJson(String unionId){
		String str="";
		Set<WxDevsnlistUtil> wxdevlist=ScanWxuserDevsnByUnionId(unionId);
		Iterator iterator = wxdevlist.iterator();
		JSONObject obj = new JSONObject();
		JSONArray jarr = new JSONArray();
		while(iterator.hasNext()){
			WxDevsnlistUtil s= (WxDevsnlistUtil) iterator.next();
			JSONObject js = new JSONObject();
			js.put("sn", s.getDevsn());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String dateString = formatter.format(s.getDate());
			js.put("date", dateString);
			jarr.add(js);
			System.out.println("ScanWxuserDevsnByUnionId :"+s.getDevsn());
		}
		obj.put("msgtype", "scandev");
		obj.put("unionId", unionId);
		obj.put("devsn", jarr);
		str = obj.toString();
		System.out.println("json : "+str);
		return str;
	}
}

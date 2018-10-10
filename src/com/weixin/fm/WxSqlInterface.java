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
import com.hibernate.db.HuserDevsnlistUtil;
import com.hibernate.db.Weixinuser;
import com.lanbao.common.Logutils;
import com.lanbao.dbdata.XiaomanyaoInterface;

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
		
	//根据 unionId 查询手机号码
	public static String GetPhoneByunionId(String unionId){
		int ret =-1;
		String phone="";
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try{
			String sql = "from Weixinuser wx where wx.unionId=?";
			Query query = session.createQuery(sql);
			query.setParameter(0, unionId);
			List<Object>list=query.list();
			 if(list!=null&&list.size()!=0){
				 Iterator iterator = list.iterator();
				 while(iterator.hasNext()){
					 Weixinuser s = (Weixinuser) iterator.next();
					 phone = s.getPhone();
					 System.out.println("find:"+unionId+"--->phone:"+phone);  
				 }
		       }else{
		    	   System.out.println("not find");     
		       }
			tx.commit();// 提交事务
			ret=0;
		}catch (Exception e) {
			System.out.println("GetPhoneByunionId:"+e.toString());
		}
		HibernateUtil.closeSession();  
		return phone;
	}
	
	public static int WxSqlBindDevSn(String unionId,String phone){//更新微信用户绑定手机号码
		int ret =-1;
		if(XiaomanyaoInterface.checkusername(phone)==-1){
			return -1;
		}
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try{
			Query query = session.createQuery("update Weixinuser wx set wx.phone=? where unionId=?");
			query.setParameter(0, phone);
			query.setParameter(1, unionId);
			query.executeUpdate();	
			ret=0;
		}catch (Exception e) {
			System.out.println("WxSqlBindDevSn:"+e.toString());
		}
		tx.commit();// 提交事务
		HibernateUtil.closeSession();  
		return ret;
	}
		
	public static String CreateWxuserDevsnByUnionId_AckJson(String unionId,String phone){
		String str="";
		Set<HuserDevsnlistUtil> wxdevlist=XiaomanyaoInterface.ScanuserDevsnByPhoneId(phone);
		Iterator iterator = wxdevlist.iterator();
		JSONObject obj = new JSONObject();
		JSONArray jarr = new JSONArray();
		while(iterator.hasNext()){
			HuserDevsnlistUtil s= (HuserDevsnlistUtil) iterator.next();
			JSONObject js = new JSONObject();
			js.put("sn", s.getDevsn());
			js.put("logo", "https://www.lanbaoai.cn/XiaoManyao/music?method=down&fileName=2018-8-6/周杰伦专辑.jpg");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String dateString = formatter.format(s.getDate());
			js.put("date", dateString);
			jarr.add(js);
			System.out.println("ScanWxuserDevsnByUnionId :"+s.getDevsn());
		}
		obj.put("msgtype", "scandev");
		obj.put("unionId", unionId);
		obj.put("phone", phone);
		obj.put("devsn", jarr);
		str = obj.toString();
		System.out.println("json : "+str);
		return str;
	}
	
	public static int deleteDevSnByUnionId(String unionId,String phone){
		int ret =-1;
		String str ="";
		Weixinuser wxUser =null; 

		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务
		String sql = "from Weixinuser wx where wx.unionId=?";
		Query query =session.createQuery(sql);//HQL创建查询语句
		query.setParameter(0,unionId);
		int delId=0;
		List<Object>list=query.list();
		if(list!=null&&list.size()!=0){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				wxUser = (Weixinuser) iterator.next();
				wxUser.setPhone("");
				System.out.println("ScanWxuserDevsnByUnionId:"+wxUser.toString());
				break;
			}	 
		}
		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭	
		return ret;
	}
	
}

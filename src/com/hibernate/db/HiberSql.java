package com.hibernate.db;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HiberSql {
	
	public static void Insert(Object obj) throws Exception {		
		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务
		session.save(obj);//保存-数据库
		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭

	}

    public static List<Object> ScanTable(String tablename){
    	List<Object> list =null;
		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务
		list= session.createQuery("from "+tablename).list();//HQL创建查询语句
		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭
		return list;
    }
    
    public static List<Object> ScanUser(String usrname){
    	List<Object> list =null;
		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务
		String sql = "from HuserManger usr where usr.usrname=?";
		Query query =session.createQuery(sql);//HQL创建查询语句
		query.setParameter(0, usrname);
		list=query.list();
		 if(list!=null&&list.size()!=0){
			 Iterator iterator = list.iterator();
			 while(iterator.hasNext()){
				 HuserManger s = (HuserManger) iterator.next();
				 System.out.println("find id:"+s.getuId()+"-->usrname:"+s.getUsrname()+"-->passwd:"+s.getPasswd()+"-->chmod:"+s.getChmod()+"-->date:"+s.getDate());  		
			 }
			 
	       }else{
	    	   System.out.println("not find");     
	       }
		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭
		return list;
    }
    
    public static void showTableMessage(){
		List<Object>list = ScanTable("HuserManger");
		Iterator iterator = list.iterator();
		while(iterator.hasNext()){
			HuserManger s = (HuserManger) iterator.next();
			System.out.println(s.getuId()+" "+ s.getUsrname()+"  "+s.getPasswd());
		}
		List<Object>list_e = ScanTable("MusicListUtil");
		Iterator iterator_e = list_e.iterator();
		while(iterator_e.hasNext()){
			MusicListUtil s = (MusicListUtil) iterator_e.next();
			System.out.println(s.toString());
		}
    }
    
	  public static void updateUsrPasswd(String  usrname,String passwd){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update HuserManger usr set usr.passwd=? where usrname=?");
		query.setParameter(0, passwd);
		query.setParameter(1, usrname);
		query.executeUpdate();
		tx.commit();// 提交事务
		HibernateUtil.closeSession();  
	  }
	  public static void updateUsrdevSn(String  usrname,String devSn){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try{
			Query query = session.createQuery("update HuserManger usr set usr.devSn=? where usrname=?");
			query.setParameter(0, devSn);
			query.setParameter(1, usrname);
			query.executeUpdate();
			tx.commit();// 提交事务
		}catch (Exception e) {
			System.out.println("updateUsrdevSn:"+e.toString());
		}
		HibernateUtil.closeSession();  
	  }
	  
	  public static void updateUsrTable(String  usrname,String passwd,String chmod){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();// 开启事务
		Query query = session.createQuery("update HuserManger usr set usr.passwd=?,usr.chmod=? where usrname=?");
		query.setParameter(0, passwd);
		query.setParameter(1, chmod);
		query.setParameter(2, usrname);
		query.executeUpdate();
		tx.commit();// 提交事务
		HibernateUtil.closeSession();  
	  }

	  public static void deleteUsrTable(String usrname){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		String sql = "delete from HuserManger usr where usr.usrname=?";
		Query query = session.createQuery(sql);
		query.setParameter(0, usrname);
		query.executeUpdate();
		tx.commit();
		HibernateUtil.closeSession();  
	  }

	public static void main(String[]args){
	
		System.out.println("start insert data :");
		
		MusicListUtil mlist = new MusicListUtil();
//		mlist.setMusicName("青花瓷");
		mlist.setMusicName("music1");
		mlist.setLogo("http://xxxxxxxxxxxx");
		Date day=new Date();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		String date =df.format(day);
		mlist.setDate(day);
//		try {
//			Insert(mlist);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		showTableMessage();
		updateUsrTable("test12", "654321","root");
		ScanUser("test12");
//		deleteUsrTable("test12");
	
	}
}

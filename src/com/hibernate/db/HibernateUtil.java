package com.hibernate.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory=null;
	private static Configuration config=null;
	private static ThreadLocal<Session> threadlocal = new ThreadLocal<Session>() ;
	
	public static Configuration getConfiguration(){
		if(config==null){
			config =new Configuration().configure();
		}
		return config;
	}
	public static Session getSession(){
		Session session = threadlocal.get() ;
		if( session == null || !session.isOpen() ){
			if( sessionFactory == null ){
				HibernateUtil.CreateDataBase();
				buildFactory();
				System.out.println("load buildSessionFactory");
			}
			session = sessionFactory.openSession() ;
			threadlocal.set(session);
			}
			return session ;
	}
	public static void closeSession(){
		Session session = threadlocal.get() ;
		threadlocal.set(null);
		if( session != null && session.isOpen()){
			session.close() ;
		}
	}

	private static void buildFactory(){
		config=new Configuration().configure();
		sessionFactory = config.buildSessionFactory();
	}
	
	public static void CreateDataBase(){		//创建数据库
		Connection conn=null;
		Statement stmt=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String mysql_user = HibernateUtil.getConfiguration().getProperties().getProperty("hibernate.connection.username");
			String mysql_password = HibernateUtil.getConfiguration().getProperties().getProperty("hibernate.connection.password");
			String mysql_url = HibernateUtil.getConfiguration().getProperties().getProperty("hibernate.connection.ipaddress");
			String databaseName = HibernateUtil.getConfiguration().getProperties().getProperty("hibernate.connection.databaseName");
			conn=DriverManager.getConnection(mysql_url, mysql_user, mysql_password);
			String sql = "CREATE DATABASE IF NOT EXISTS "+databaseName+" default charset=utf8";
			stmt = conn.createStatement();
			int count = stmt.executeUpdate(sql);
			System.out.println("create database ok:"+mysql_url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("create database failed:"+e.getMessage());
			e.printStackTrace();
		}finally{
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
		}		
	}
}

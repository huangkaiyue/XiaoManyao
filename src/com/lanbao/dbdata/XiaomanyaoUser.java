package com.lanbao.dbdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.json.JSONArray;

public class XiaomanyaoUser {
	private String serverip="192.168.1.15";
//	private String serverip="47.95.230.203";
	private String serverUrl = "jdbc:mysql://"+serverip+":3306?useUnicode=true&characterEncoding=utf8";
	public static String databaseName ="xiaomanyao";//useUnicode=true&characterEncoding=utf8
	private String url = "jdbc:mysql://"+serverip+":3306/"+databaseName+"?useUnicode=true&characterEncoding=utf8";
	private String mysql_user = "root";
	private String mysql_password = "123456";
//	private String mysql_password = "kaiyue123456.";
	public static String tableName = "usertable";
	MysqlcallBack inter;
	public XiaomanyaoUser(MysqlcallBack inter){
		this.inter=inter;
	}
	
	public void CreateDataBase(String databaseName){		//创建数据库
		Connection conn=null;
		Statement stmt=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(serverUrl, mysql_user, mysql_password);
			String sql = "CREATE DATABASE IF NOT EXISTS "+databaseName;
			stmt = conn.createStatement();
			int count = stmt.executeUpdate(sql);
			System.out.println("create database ok");
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
	
	public void CreateTable(String tableName){		//创建表
		Connection conn=null;
		Statement stmt=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(url, mysql_user, mysql_password);
			String sql = "CREATE TABLE IF NOT EXISTS "+tableName+"(id INT PRIMARY KEY AUTO_INCREMENT,usrname VARCHAR(64) NOT NULL unique,passwd VARCHAR(64),date VARCHAR(64)) default charset=utf8;";
			stmt = conn.createStatement();
			int count = stmt.executeUpdate(sql);
			System.out.println("影响了"+count+"行！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	public int ALTER_table(){
		int ret =-1;
		Connection conn=null;
		Statement stmt=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(url, mysql_user, mysql_password);
			String sql = "ALTER TABLE "+tableName+"MODIFY COLUMN usrname VARCHAR(64) NOT NULL unique;";
			stmt = conn.createStatement();
			int count = stmt.executeUpdate(sql);
			System.out.println("影响了"+count+"行！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		return ret;
	}
	
	public int InsertData(String usrname,String passwd,String date){
		int ret =-1;
		Connection conn=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(url, mysql_user, mysql_password);
			PreparedStatement psql = conn.prepareStatement("insert into "+tableName+ "(usrname,passwd,date)"+"values(?,?,?)");
			psql.setString(1, usrname);
			psql.setString(2, passwd);
			psql.setString(3, date);
			psql.executeUpdate();
			psql.close();
			ret=0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			System.out.println("数据库数据插入成功！"+"\n");
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
		}		
		return ret;
	}
	public void fetchDatabaeByName(JSONArray json,String usrname){   //读取数据函数
		Connection conn=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn =DriverManager.getConnection(url, mysql_user, mysql_password);
			Statement statement = conn.createStatement();  //用statement 来执行sql语句
			PreparedStatement psql = conn.prepareStatement("select * from "+tableName+ " where usrname=?");
			psql.setString(1, usrname);
			System.out.println("---------------------------------------------------");
			System.out.println("表中的数据有:");
			System.out.println("姓名" + "\t" + "密码" + "\t" + "注册日期");
			System.out.println("---------------------------------------------------");
			String gusrname="",passwd = "",date="";
			ResultSet rs=null; 
			rs = psql.executeQuery();       
			while(rs.next()){
				gusrname = rs.getString("usrname");
	            passwd = rs.getString("passwd");
	            date = rs.getString("date");
	            inter.GetSqlData(json,gusrname, passwd, date);
			}
			rs.close();
	        }catch(SQLException e){
	        	System.out.println("error 1"+e.getMessage());
	            e.printStackTrace();
	        }catch (Exception e) {
	        	System.out.println("error 2"+e.getMessage());
	            e.printStackTrace();
	        }finally{
	                System.out.println("数据库数据读取成功！"+"\n");
	        }
	}
	public void fetchdata(JSONArray jarr){   //读取数据函数
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection(url, mysql_user, mysql_password);
				
			Statement statement = conn.createStatement();  //用statement 来执行sql语句
			String sql = "select * from "+tableName;   //这是sql语句中的查询某个表，注意后面的emp是表名！！！
			ResultSet rs = statement.executeQuery(sql);  //用于返回结果
			System.out.println("---------------------------------------------------");
			System.out.println("表中的数据有:");
			System.out.println("姓名" + "\t" + "问题" + "\t" + "答案"+ "\t" + "图片"+ "\t" + "语音");
			System.out.println("---------------------------------------------------");
			String usrname = "",passwd="",date="";
			long time =0;
			while(rs.next()){
				usrname = rs.getString("usename");
				passwd = rs.getString("passwd");
				date = rs.getString("date");
	            inter.GetSqlData(jarr,usrname, passwd,date);
//	            System.out.println(usename + "\t" + question + "\t" + ack+ "\t" + picture+ "\t" + voice);
			}
			rs.close();
	        }catch(SQLException e){
	        	System.out.println("error 1"+e.getMessage());
	            e.printStackTrace();
	        }catch (Exception e) {
	        	System.out.println("error 2"+e.getMessage());
	            e.printStackTrace();
	        }finally{
	                System.out.println("数据库数据读取成功！"+"\n");
	        }
	}	
	public int checkusernameAndpasswd(String usrname,String passwd){
		int ret =-1;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection(url, mysql_user, mysql_password);
				
			Statement statement = conn.createStatement();  //用statement 来执行sql语句
			String sql = "select * from "+tableName+" where usrname ="+usrname;  
			ResultSet rs = statement.executeQuery(sql);  //用于返回结果
			String gpasswd="";
			while(rs.next()){
				gpasswd = rs.getString("passwd");
				if(gpasswd.equals(passwd)){
					ret=0;
					System.out.println("passwd is same");
				}else{
					ret=-2;
				}
				System.out.println("gpasswd:"+gpasswd);
			}
			rs.close();
	        }catch(SQLException e){
	        	System.out.println("error 1"+e.getMessage());
	            e.printStackTrace();
	        }catch (Exception e) {
	        	System.out.println("error 2"+e.getMessage());
	            e.printStackTrace();
	        }finally{
//	                System.out.println("数据库数据读取成功！"+"\n");
	        }
		return ret;
	}
	
	public int checkusername(String usrname){
		int ret =-1;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection(url, mysql_user, mysql_password);
				
			Statement statement = conn.createStatement();  //用statement 来执行sql语句
			String sql = "select * from "+tableName+" where usrname ="+usrname;  
			ResultSet rs = statement.executeQuery(sql);  //用于返回结果
			String gpasswd="";
			if(rs.next()){
				gpasswd = rs.getString("passwd");
				ret=0;	
				System.out.println("gpasswd:"+gpasswd);
			}
			rs.close();
	        }catch(SQLException e){
	        	System.out.println("error 1"+e.getMessage());
	            e.printStackTrace();
	        }catch (Exception e) {
	        	System.out.println("error 2"+e.getMessage());
	            e.printStackTrace();
	        }finally{
	                System.out.println("数据库数据读取成功！"+"\n");
	        }
		return ret;
	}
	
	public int updatepasswd(String usrname,String newpasswd){
		int ret =-1;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection(url, mysql_user, mysql_password);
			String sql = "UPDATE "+tableName+" SET  passwd=?"+" WHERE usrname=?";  
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newpasswd);
			ps.setString(2, usrname);
			int sqlRet =ps.executeUpdate();	//返回0，操作无效  ，返回1 表示更新成功
			if(sqlRet==1){
				ret = 0;
			}else{
				ret=-1;
			}
	        }catch(SQLException e){
	        	System.out.println("error 1"+e.getMessage());
	            e.printStackTrace();
	        }catch (Exception e) {
	        	System.out.println("error 2"+e.getMessage());
	            e.printStackTrace();
	        }finally{
	                System.out.println("数据库数据读取成功！"+"\n");
	        }
		return ret;
	}
}

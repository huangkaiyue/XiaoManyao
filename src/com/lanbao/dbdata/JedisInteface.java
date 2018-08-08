package com.lanbao.dbdata;

import java.util.List;

import redis.clients.jedis.Jedis;

public class JedisInteface {
	
	private static String ipaddress = "47.95.230.203";
//	private static String ipaddress = "localhost";
	private static int port = 6379;
	
	private static List<String> hmget(String key,String... fields){
			Jedis jedis = new Jedis(ipaddress, port);
			jedis.auth("123456");
			List<String> list=jedis.hmget(key,fields);
			jedis.close();  
			return list;
	}
	private static void hmset(String key,String field,String value){
		Jedis jedis = new Jedis(ipaddress, port);
		jedis.auth("123456");
		jedis.hset(key, field, value);
		jedis.close();  
	}
	
	public static List<String> getPhoneCode(String key){
			return hmget(key,"code","time","token");
	}
	
	public static void setPhoneCode(String key,String code,String time){
		hmset(key,"code",code);
		hmset(key,"time",time);
	}
	public static void setPhoneToken(String key,String token,String time){
		hmset(key,"token",token);
		hmset(key,"time",time);
	}
	public static void showPhoneCode(String key){
		List<String> list=JedisInteface.getPhoneCode(key);
		for(int i=0;i<list.size();i++){
			System.out.println("index["+i+"]:"+list.get(i).toString());
		}
	}
	
//	private static List<String> hmget(String key,String... fields){
//		List<String> list=JedisUtil.getHashMapString(key, fields);
//		return list;
//	}
//	private static void hmset(String key,String field,String value){
//		JedisUtil.setHString(key, field, value);
//	}
//	
//	public static List<String> getPhoneCode(String key){
//		return JedisUtil.getHashMapString(key, "code","time","token");
//	}
//	
//	public static void setPhoneCode(String key,String code,String time){
//		JedisUtil.setHString(key, "code",code);
//		JedisUtil.setHString(key, "time",time);
//	}
//	public static void setPhoneToken(String key,String token,String time){
//		JedisUtil.setHString(key, "token",token);
//		JedisUtil.setHString(key, "time",time);
//	}
//	public static void showPhoneCode(String key){
//		List<String> list=JedisInteface.getPhoneCode(key);
//		for(int i=0;i<list.size();i++){
//			System.out.println("index["+i+"]:"+list.get(i).toString());
//		}
//	}
}

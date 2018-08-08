package com.lanbao.dbdata;

import java.util.List;

public class CheckCode {
//	private static String phone ="188814119548";
//	private static String code ="1234";
	private static int TIME_OUT =1000*60*5;
	private static int AUTO_LOGIN_TIME_OUT =1000*60*60*24*10;
	public static final int invalid_code=-1;
	public static final int timeout_code=-2;
	public static final int ok_code=0;
	
	public static void CachePhoneCode(String phone,String code){
		long time=System.currentTimeMillis();
		JedisInteface.setPhoneCode(phone, code,""+time);
	}
	public static void CachePhoneToken(String phone,String token){
		long time=System.currentTimeMillis();
		JedisInteface.setPhoneToken(phone,  token,""+time);
	}
	public static int checkPhoneCode(String phone,String code){
//		System.out.println("start checkPhoneCode");
		List<String> list = JedisInteface.getPhoneCode(phone);
		if(list==null||list.size()<=0){
			System.out.println("not found phone:"+phone);
			return invalid_code;
		}
		for(int i=0;i<list.size();i++){
			if(list.get(0)==null){
				System.out.println("invalid_code not found phone :"+phone);
				return invalid_code;
			}
			if(list.get(0).toString().equals(code)){
				long currentTime=System.currentTimeMillis();
//				System.out.println("list.get(1):"+list.get(1).toString());
				long time = Long.parseLong(list.get(1).toString());
//				System.out.println("Long.parseLong:"+time);
				if((currentTime-time)>TIME_OUT){
					System.out.println("timeout currentTime:"+currentTime+"--->time:"+time);
					return timeout_code;
				}
//				System.out.println("check code ok:"+currentTime+"--->time:"+time);
				return ok_code;
			}else{
				return invalid_code;
			}
		}
		return invalid_code;
	}
	
	
	public static int checkPhoneToken(String phone,String token){
		
		List<String> list = JedisInteface.getPhoneCode(phone);
		if(list==null||list.size()<=0){
			return invalid_code;
		}
		for(int i=0;i<list.size();i++){
			if(list.get(0)==null||list.get(2)==null){
				System.out.println("invalid_code not found phone :"+phone);
				return invalid_code;
			}
			if(list.get(2).toString().equals(token)){
				long currentTime=System.currentTimeMillis();
				long time = Long.parseLong(list.get(1).toString());
				if((currentTime-time)>AUTO_LOGIN_TIME_OUT){
					System.out.println("timeout currentTime:"+currentTime+"--->time:"+time);
					return timeout_code;
				}
				System.out.println("check code ok:"+currentTime+"--->time:"+time+"-------->token:"+list.get(2).toString());
				return ok_code;
			}else{
				return invalid_code;
			}
		}
		return invalid_code;
	}
	
//	public static void main(String []args){
//		
//		CachePhoneCode(phone, code);
//		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		CachePhoneToken(phone, "123456784232");
////		JedisInteface.showPhoneCode("188814119548");
//		checkPhoneCode(phone, code);
//	}
	
}

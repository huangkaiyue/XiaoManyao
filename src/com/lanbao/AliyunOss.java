package com.lanbao;

import java.net.URL;
import java.util.Date;

import net.sf.json.JSONObject;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;
import com.aliyun.oss.OSSClient;
import com.lanbao.common.Common;

public class AliyunOss {

	public String GetOssUrlByName(String ossName){	//���oss keyname ��ȡ�����������ӵ�
		
		System.out.println("Send message id is: ........" );
		String bucketName = "lanbaoai123";
		Common obj = new Common();
		OSSClient ossClient = new OSSClient(obj.getAccountEndpoint(), obj.getAccessId(), obj.getAccessKey());
		Date expiration = new Date(new Date().getTime()+600);
		URL url = ossClient.generatePresignedUrl(bucketName, ossName, expiration);
		
		
		 		
		JSONObject json = new JSONObject();
		json.put("msgtype", "downfile");
		json.put("url", ""+url.toString());
//		json.put("md5", ""+url);
//		json.put("size", ""+url);
		System.out.println("GetOssUrlByName ..."+json.toString());
		return json.toString();
	}
  
}

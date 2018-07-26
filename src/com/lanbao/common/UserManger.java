package com.lanbao.common;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.QueueMeta;

public class UserManger {

    public static void main(String[] args) {
    	String queueName = "linkeweici00001";
    	
    }
	
    public boolean CreateQueName(String queueName){
    	boolean state=false;
    	Common com = new Common();
    	CloudAccount account = new CloudAccount(com.getAccessId(), com.getAccessKey(), com.getAccountEndpoint());
        MNSClient client = account.getMNSClient(); // 在程序中，CloudAccount以及MNSClient单例实现即可，多线程安全
        
        QueueMeta meta = new QueueMeta(); //生成本地QueueMeta属性，有关队列属性详细介绍见https://help.aliyun.com/document_detail/27476.html
        meta.setQueueName(queueName);  // 设置队列名
        meta.setPollingWaitSeconds(15);
        meta.setMaxMessageSize(2048L);
        try {
        	
            CloudQueue queue = client.createQueue(meta);
            state=true;
            
        } catch (ClientException ce)
        {
            System.out.println("Something wrong with the network connection between client and MNS service."
                   + "Please check your network and DNS availablity.");
            ce.printStackTrace();
        } catch (ServiceException se)
        {
            se.printStackTrace();
            System.out.println("MNS exception requestId:" + se.getRequestId()+ se);
            if (se.getErrorCode() != null) {
                if (se.getErrorCode().equals("QueueNotExist"))
                {
                    System.out.println("Queue is not exist.Please create before use");
                } else if (se.getErrorCode().equals("TimeExpired"))
                {
                    System.out.println("The request is time expired. Please check your local machine timeclock");
                }
            /*
            you can get more MNS service error code from following link:
            https://help.aliyun.com/document_detail/mns/api_reference/error_code/error_code.html
            */
            }
        } catch (Exception e)
        {
            System.out.println("Unknown exception happened!");
            e.printStackTrace();
        }
        client.close();  // 程序退出时，需主动调用client的close方法进行资源释放    	
    	return state;
    } 
 
    public boolean deleteQueName(String queueName){
    	boolean state=false;
    	Common com = new Common();
        CloudAccount account = new CloudAccount(com.getAccessId(), com.getAccessKey(), com.getAccountEndpoint());
        MNSClient client = account.getMNSClient(); // 在程序中，CloudAccount以及MNSClient单例实现即可，多线程安全
        try{
            CloudQueue queue = client.getQueueRef(queueName);
            queue.delete();
        } catch (ClientException ce)
        {
            System.out.println("Something wrong with the network connection between client and MNS service."
                    + "Please check your network and DNS availablity.");
            ce.printStackTrace();
        } catch (ServiceException se)
        {
            se.printStackTrace();
        } catch (Exception e)
        {
            System.out.println("Unknown exception happened!");
            e.printStackTrace();
        }
        client.close();    	
    	return state;
    } 
    
}

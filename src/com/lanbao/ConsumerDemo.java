package com.lanbao;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;
import com.lanbao.common.Common;

public class ConsumerDemo {
    public static void main(String[] args) {
    	ConsumerDemo obj = new ConsumerDemo();
    	while(true){
    		String message =obj.getMessage("kaiyue");
    		System.out.println("recv message: "+message);
    		break;
    	}
    }
    
    private String getMessage(String queName){
    	String message = "";
    	Common com = new Common();
    	CloudAccount account = new CloudAccount(com.getAccessId(), com.getAccessKey(), com.getAccountEndpoint());
        MNSClient client = account.getMNSClient(); // �ڳ����У�CloudAccount�Լ�MNSClient����ʵ�ּ��ɣ����̰߳�ȫ
        
        try{
            CloudQueue queue = client.getQueueRef(queName);
            Message popMsg = queue.popMessage();
            if (popMsg != null){
//                System.out.println("message handle: " + popMsg.getReceiptHandle());
//                System.out.println("message body: " + popMsg.getMessageBodyAsString());
                message=popMsg.getMessageBodyAsString();
//                System.out.println("message id: " + popMsg.getMessageId());
//                System.out.println("message dequeue count:" + popMsg.getDequeueCount());
                //ɾ���Ѿ�ȡ����ѵ���Ϣ
                queue.deleteMessage(popMsg.getReceiptHandle());
//                System.out.println("delete message successfully.\n");
            }
            else{
                System.out.println("message not exist in this queName:"+queName);
            }
        } catch (ClientException ce)
        {
            System.out.println("Something wrong with the network connection between client and MNS service."
                    + "Please check your network and DNS availablity.");
            ce.printStackTrace();
        } catch (ServiceException se)
        {
            se.printStackTrace();
//            logger.error("MNS exception requestId:" + se.getRequestId(), se);
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
        client.close();
        return  message;
    }
    
    public static int SendMessage(String queName,String mstr){
    	int ret =-1;
       	Common com = new Common();
    	CloudAccount account = new CloudAccount(com.getAccessId(), com.getAccessKey(), com.getAccountEndpoint());
        MNSClient client = account.getMNSClient(); // �ڳ����У�CloudAccount�Լ�MNSClient����ʵ�ּ��ɣ����̰߳�ȫ
        try {
            CloudQueue queue = client.getQueueRef(queName);
            Message message = new Message();
            message.setMessageBody(mstr);
            Message putMsg = queue.putMessage(message);
            System.out.println("Send message id is: " + putMsg.getMessageId());
            ret=0;
        } catch (ClientException ce)
        {
            System.out.println("Something wrong with the network connection between client and MNS service."
                   + "Please check your network and DNS availablity.");
            ce.printStackTrace();
        } catch (ServiceException se)
        {
            se.printStackTrace();
            System.out.println("MNS exception requestId:" + se.getRequestId()+se);
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
        client.close();  // �����˳�ʱ������������client��close����������Դ�ͷ�
        return ret;
    }
}

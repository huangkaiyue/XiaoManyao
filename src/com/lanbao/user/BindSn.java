package com.lanbao.user;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.lanbao.common.HttpServletUtils;
import com.lanbao.dbdata.XiaomanyaoInterface;
import com.opensymphony.xwork2.ActionSupport;

public class BindSn extends ActionSupport{
	public String bindsn() throws IOException{
		String boby=HttpServletUtils.getRequestBoby(ServletActionContext.getRequest());
		if(boby==null||boby.equals("")){
			AckRequest(403,"无效的请求参数");
			return NONE;
		}
//		System.out.println("BindSn recv boby:"+boby);
		UserReq req =(UserReq)JSONObject.toBean(JSONObject.fromObject(boby),UserReq.class);
		System.out.println(req.toString());
		if(req.getMsgtype()==null){
			AckRequest(403,"无效的请求参数");
		}else if(req.getMsgtype().equals("bindsn")){
			if(req.getUsername()!=null&&req.getDevsn()!=null){
				XiaomanyaoInterface.updatedevSn(req.getUsername(),req.getDevsn());
				AckRequest(200,"绑定设备成功");
			}else{
				AckRequest(403,"无效的请求参数");
			}
		}else if(req.getMsgtype().equals("cleansn")){
			if(req.getUsername()!=null){
				XiaomanyaoInterface.updatedevSn(req.getUsername(),"");
				AckRequest(200,"清除绑定记录成功");
			}else{
				AckRequest(403,"无效的请求参数");
			}
		}	
		return NONE;
	}
	private void AckRequest(int result,String resdata) throws IOException{
		String jsondata = AckInterface.CreateAckJson("bindsn", resdata, "", result);
		HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(),jsondata);
	}
}

package com.image.version;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.lanbao.common.FileInter;
import com.lanbao.common.HttpServletUtils;
import com.lanbao.common.Logutils;
import com.opensymphony.xwork2.ActionSupport;
import com.start.server.ConfigServer;

public class Imageversion extends ActionSupport{ 
	
	private String versionHead ="H1_ROM_Release_";
	
	private List<File> file1 ;  
	private List<String> file1FileName ; // 文件名 
	private List<String> file1ContentType ;  // 文件的类型(MIME)
	
    public List<File> getFile1() {  
        return file1;  
    }  
    public void setFile1(List<File> file1) {  
        this.file1 = file1;  
    }  
    public List<String> getFile1FileName() {  
        return file1FileName;  
    }  
    public void setFile1FileName(List<String> file1FileName) {  
        this.file1FileName = file1FileName;  
    }  
    public List<String> getFile1ContentType() {  
        return file1ContentType;  
    }  
    public void setFile1ContentType(List<String> file1ContentType) {  
        this.file1ContentType = file1ContentType;  
    }  
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub

		// 获取上传的目录路径		
		if(file1==null){
			System.out.println("not upload file ......");
			return NONE;
		}
		
		//H1_ROM_Release_20180815_1022_0.0.1.bin
		long version = 0;
		String verStr ="";
		Logutils.e("file1FileName.get(0):"+file1FileName.get(0));
		if(file1FileName.get(0).length()<versionHead.length()){
			HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(), "无效固件版本1:"+file1FileName.get(0));
			return NONE;
		}
		Logutils.e("check:"+file1FileName.get(0));
		String headStr=file1FileName.get(0).substring(0,versionHead.length());
		Logutils.e("headStr:"+headStr);
		
		if(headStr.equals(versionHead)){
			String sp[] = file1FileName.get(0).split("_");
			verStr = sp[3]+sp[4];
			version = Long.parseLong(verStr);
			System.out.println("upload version:"+verStr+"--->version number:"+version);
		}else{
			HttpServletUtils.AckRequestResponse(ServletActionContext.getResponse(), "无效固件版本2:"+file1FileName.get(0)+"\n--->head:"+headStr);
			return NONE;
		}    
        String imageDir = ConfigServer.getInstance().getImageVersionDir(); 		
        for(int i = 0 ; i < file1.size() ; i++ )  {  
            OutputStream os = new FileOutputStream(new File(imageDir+"/",file1FileName.get(i)));  
            InputStream is = new FileInputStream(file1.get(i));  
            byte[] buf = new byte[1024];  
            int length = 0 ;  
              
            while(-1 != (length = is.read(buf) ) )  {  
                os.write(buf, 0, length) ;  
            }  
              
            is.close();  
            os.close();  
        }  
        String MD5=FileInter.getMd5ByFile(imageDir+"/"+file1FileName.get(0)) ; 
        XmyVerSql.InsertImageVersion(file1FileName.get(0),MD5,verStr);
		System.out.println("MD5:"+MD5);
		System.out.println("versionMessage:"+verStr);
		System.out.println("image:"+file1FileName.get(0));
		System.out.println("imageDir:"+imageDir);
		return NONE;
	}
}

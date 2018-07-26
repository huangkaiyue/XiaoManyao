package com.image.version;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.lanbao.common.Common;
import com.lanbao.common.FileInter;
import com.opensymphony.xwork2.ActionSupport;
import com.xiaomanyao.music.XiaomanyaoMusicSql;

public class Imageversion extends ActionSupport{ 
	private String versionMessage="" ;  

	private List<File> file1 ;  
	private List<String> file1FileName ; // 文件名 
	private List<String> file1ContentType ;  // 文件的类型(MIME)
	
	public String getVersionMessage() {
		return versionMessage;
	}
	public void setVersionMessage(String versionMessage) {
		this.versionMessage = versionMessage;
	}
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
        String path = ServletActionContext.getServletContext().getRealPath("/XiaomanyaoFile"); 
        String imageDir =path+"/"+"xiaomanyaoImageVesion";
        File dir = new File(imageDir);
		if(dir.exists()){
			System.out.println("dir is exists");
		}else{
			System.out.println("dir not exists");
			dir.mkdir();
		}
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
        XmyVerSql.InsertImageVersion(file1FileName.get(0),MD5,versionMessage);
		System.out.println("MD5:"+MD5);
		System.out.println("versionMessage:"+versionMessage);
		System.out.println("image:"+file1FileName.get(0));
		System.out.println("imageDir:"+imageDir);
		return NONE;
	}

}

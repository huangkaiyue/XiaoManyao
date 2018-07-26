package com.xiaomanyao.music;

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

public class FileUpLoad extends ActionSupport{ 
	private String author="" ; 
	private String album="" ;  
	private List<File> file1 ;  
	private List<String> file1FileName ; // 文件名 
	private List<String> file1ContentType ;  // 文件的类型(MIME)
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
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
//		uploadFile(ServletActionContext.getRequest(),ServletActionContext.getResponse());    
		// 获取上传的目录路径		
		if(file1==null){
			System.out.println("not upload file ......");
			return NONE;
		}
        String path = ServletActionContext.getServletContext().getRealPath("/XiaomanyaoFile");  
        String  saveDir = Common.createDirBydate(path);
       
        for(int i = 0 ; i < file1.size() ; i++ )  {  
            OutputStream os = new FileOutputStream(new File(path+"/"+saveDir,file1FileName.get(i)));  
            InputStream is = new FileInputStream(file1.get(i));  
            byte[] buf = new byte[1024];  
            int length = 0 ;  
              
            while(-1 != (length = is.read(buf) ) )  {  
                os.write(buf, 0, length) ;  
            }  
              
            is.close();  
            os.close();  
        }  
        String md5 =FileInter.getMd5ByFile(path+"/"+saveDir+"/"+file1FileName.get(1));
        XiaomanyaoMusicSql.InsertMusicList(file1FileName.get(1), author, album, file1FileName.get(0),saveDir,md5);
		System.out.println("author:"+author);
		System.out.println("album:"+album);
		System.out.println("logo:"+file1FileName.get(0));
		System.out.println("musicname:"+file1FileName.get(1));
		System.out.println("date:"+saveDir);
		return NONE;
	}
}

package com.xiaomanyao.music;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.lanbao.common.Common;
import com.lanbao.common.FileInter;
import com.opensymphony.xwork2.ActionSupport;

public class Musicupload extends ActionSupport{ 
	private String author="" ; 
	private String album="" ;  
	private String albummessage="";
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
	public String getAlbummessage() {
		return albummessage;
	}
	public void setAlbummessage(String albummessage) {
		this.albummessage = albummessage;
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
		String serverSelect = ServletActionContext.getRequest().getParameter("serverSelect").toString();
		if(serverSelect.equals("1")){
			System.out.println("upload is localserver");
		}else{
			System.out.println("upload is aliyun server");
		}
        String path = ServletActionContext.getServletContext().getRealPath("/XiaomanyaoFile");  
        String  saveDir = Common.createDirBydate(path);
        boolean ret =XiaomanyaoMusicSql.checkAlbum(album);
		System.out.println("author:"+author);
		System.out.println("album:"+album);
		System.out.println("albummessage:"+albummessage);
		System.out.println("logo:"+file1FileName.get(0));
		System.out.println("musicname:"+file1FileName.get(1));
		System.out.println("logoHorizontal:"+file1FileName.get(2));
		System.out.println("date:"+saveDir);
		if(ret){	//专辑已经存在，只需要上传歌曲
			SaveRecvFile(path+"/"+saveDir,file1.get(1),file1FileName.get(1));
		}else{	//上传歌曲，专辑封面
			for(int i = 0 ; i < file1.size() ; i++ )  {  
				SaveRecvFile(path+"/"+saveDir,file1.get(i),file1FileName.get(i));
			}
		}
		String md5 =FileInter.getMd5ByFile(path+"/"+saveDir+"/"+file1FileName.get(1));
		XiaomanyaoMusicSql.InsertMusicList(file1FileName.get(1), author, album, file1FileName.get(0),saveDir,md5,file1FileName.get(2),albummessage);
		System.out.println("upload ok");
		return NONE;
	}
	
	private void SaveRecvFile(String saveDir,File file,String destname) throws IOException{
        OutputStream os = new FileOutputStream(new File(saveDir,destname));  
        InputStream is = new FileInputStream(file);  
        byte[] buf = new byte[1024];  
        int length = 0 ;  
          
        while(-1 != (length = is.read(buf) ) )  {  
            os.write(buf, 0, length) ;  
        }  
        is.close();  
        os.close();  
	}
	
}

package com.hibernate.db;

import java.util.Date;

//һ�� ����
public class MusicListUtil implements Comparable<MusicListUtil>{

	private int mId;
	private String musicName;
	private String author;
	private String logo;
	private String pices;
	private String saveDir;
	private String md5;
	private Date date;
	private AlbumUtil album_id;

	public int getmId() {
		return mId;
	}


	public void setmId(int mId) {
		this.mId = mId;
	}


	public String getMusicName() {
		return musicName;
	}


	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSaveDir() {
		return saveDir;
	}


	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}
	
	public String getPices() {
		return pices;
	}


	public void setPices(String pices) {
		this.pices = pices;
	}
	
	public String getMd5() {
		return md5;
	}


	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}

	public AlbumUtil getAlbum_id() {
		return album_id;
	}


	public void setAlbum_id(AlbumUtil album_id) {
		this.album_id = album_id;
	}
	@Override
	public String toString() {
		return "MusicListUtil [mId=" + mId + ", musicName=" + musicName+ ", author=" + author+ ",  logo=" + logo
				+ ", pices="+pices+", saveDir="+saveDir+", date=" + date + "]";
	}


	@Override
	public int compareTo(MusicListUtil o) {
		// TODO Auto-generated method stub
	      if(this.mId>o.getmId()){
	            return 1;
	        }
	        else if(this.mId<o.getmId()){
	            return -1;
	        }
	        else{
	            return 0;
	        }
	}
	
}

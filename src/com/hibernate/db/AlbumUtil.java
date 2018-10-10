package com.hibernate.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AlbumUtil implements Comparable<AlbumUtil>{
	private int mId;
	private String author;
	private String albumName;
	private String logo;
	private String savedir;
	private String pices;
	private String albmMessage;
	private String logoHorizontal;
	private Date date;
	private Set<MusicListUtil> albums = new HashSet<MusicListUtil>();
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getPices() {
		return pices;
	}


	public void setPices(String pices) {
		this.pices = pices;
	}
	
	public String getAlbmMessage() {
		return albmMessage;
	}
	public void setAlbmMessage(String albmMessage) {
		this.albmMessage = albmMessage;
	}
	public String getLogoHorizontal() {
		return logoHorizontal;
	}
	public void setLogoHorizontal(String logoHorizontal) {
		this.logoHorizontal = logoHorizontal;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSavedir() {
		return savedir;
	}
	public void setSavedir(String savedir) {
		this.savedir = savedir;
	}
	public Set<MusicListUtil> getAlbums() {
		return albums;
	}
	public void setAlbums(Set<MusicListUtil> albums) {
		this.albums = albums;
	}
	@Override
	public String toString() {
		return "MusicListUtil [mId=" + mId + ", author=" + author+ ", albumName=" + albumName+ ",  logo=" + logo
				+ ", pices="+pices+", savedir="+savedir+", albmMessage="+albmMessage+", date=" + date + "]";
	}
	@Override
	public int compareTo(AlbumUtil o) {
		// TODO Auto-generated method stub
		System.out.println("test:"+o.getmId());
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

package com.hibernate.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AlbumUtil {
	private int mId;
	private String albumName;
	private String logo;
	private String savedir;
	private String pices;
	private Date date;
	private Set<MusicListUtil> albums = new HashSet<MusicListUtil>();
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
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
}

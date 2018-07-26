package com.xiaomanyao.music;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hibernate.db.AlbumUtil;
import com.hibernate.db.HiberSql;
import com.hibernate.db.HibernateUtil;
import com.hibernate.db.MusicListUtil;
import com.lanbao.common.Logutils;

public class XiaomanyaoMusicSql {
	
	public static int InsertMusicList(String musicName,String author,String albumName,String logo,String saveDir,String md5){
		int ret =-1;
		MusicListUtil mlist = new MusicListUtil();
		mlist.setMusicName(musicName);
		mlist.setAuthor(author);
		mlist.setLogo(logo);
		mlist.setPices("2$");
		mlist.setSaveDir(saveDir);
		mlist.setMd5(md5);
		Date day=new Date();    
		mlist.setDate(day);
		AlbumUtil albumutil =null; 

		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务
		String sql = "from AlbumUtil al where al.albumName=?";
		Query query =session.createQuery(sql);//HQL创建查询语句
		query.setParameter(0,albumName);

		List<Object>list=query.list();
		if(list!=null&&list.size()!=0){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				albumutil = (AlbumUtil) iterator.next();
				System.out.println("getAlbumName:"+albumutil.getAlbumName()+"getmId:"+albumutil.getmId()); 
			}	 
			mlist.setAlbum_id(albumutil);
			session.save(mlist);//保存-数据库
		}else{
    	   System.out.println("not find");     
			albumutil = new AlbumUtil();
			albumutil.setAlbumName(albumName);
			albumutil.setSavedir(saveDir);
			mlist.setPices("8$");
			albumutil.setDate(day);
			albumutil.setLogo(logo);
			mlist.setAlbum_id(albumutil);
			session.save(albumutil);//保存-数据库
			session.save(mlist);//保存-数据库
       	}
		
		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭
		return ret;
	}
	
	public static Set<MusicListUtil> ScanMusicListByAlbumId(int Albumid,String downurl){
		int ret =-1;

		AlbumUtil albumutil =null; 

		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务

		albumutil = (AlbumUtil) session.get(AlbumUtil.class, Albumid);
		Set<MusicListUtil> mlist=albumutil.getAlbums();
		System.out.println("getAlbumName:"+albumutil.getAlbumName());
		
		Iterator iterator =mlist.iterator();
		while(iterator.hasNext()){
			MusicListUtil s= (MusicListUtil) iterator.next();
			System.out.println("ScanMusicListByAlbumId--->getMusicName:"+s.getMusicName());
		}
		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭
		return mlist;
	}
	
	public static String ScanMusicListByAlbumName(String albumName,String downurl){
		int ret =-1;
		String str ="";
		AlbumUtil albumutil =null; 

		Session session = HibernateUtil.getSession();// 创建session (代表一个会话，与数据库连接的会话)
		Transaction tx = session.beginTransaction();// 开启事务
		String sql = "from AlbumUtil al where al.albumName=?";
		Query query =session.createQuery(sql);//HQL创建查询语句
		query.setParameter(0,albumName);
		List<Object>list=query.list();
		if(list!=null&&list.size()!=0){
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				albumutil = (AlbumUtil) iterator.next();
				System.out.println("getAlbumName:"+albumutil.getAlbumName()+"getmId:"+albumutil.getmId()); 
			}	 
		}
		Set<MusicListUtil> mlist=albumutil.getAlbums();
		Iterator iterator = mlist.iterator();
		while(iterator.hasNext()){
			MusicListUtil s= (MusicListUtil) iterator.next();
			System.out.println("ScanMusicListByAlbumName-->getMusicName:"+s.getMusicName());
		}
		System.out.println("size:"+mlist.size());

		tx.commit();// 提交事务
		HibernateUtil.closeSession();// 关闭
		return str;
	}

	public static List<Object> ScanAlbum(){
		List<Object> albumutil= HiberSql.ScanTable("AlbumUtil");
		
		Iterator iterator = albumutil.iterator();
		while(iterator.hasNext()){
			AlbumUtil album= (AlbumUtil) iterator.next();
//			System.out.println(album.toString());
		}
		
		return albumutil;
	}

}

package com.xiaomanyao.music;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hibernate.db.AlbumUtil;
import com.hibernate.db.MusicListUtil;
import com.start.server.ConfigServer;

public class XiaomanMusicAppInterface {
	
	public static String ScanAlbumList(){
		String str = "";
		String downurl = ConfigServer.getInstance().getDownUrl();
		String httpsdownurl = ConfigServer.getInstance().getHttpsDownUrl();
		List<Object>list_e =XiaomanyaoMusicSql.ScanAlbum();
		if(list_e==null){
			return str;
		}
		Iterator<Object> iterator_e = list_e.iterator();
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		while(iterator_e.hasNext()){
			AlbumUtil s = (AlbumUtil) iterator_e.next();
			JSONObject obj = new JSONObject();
			obj.put("AlbumName", s.getAlbumName());
			obj.put("author", s.getAuthor());
			obj.put("logo", httpsdownurl+s.getSavedir()+"/"+s.getLogo());
			obj.put("logoHorizontal", httpsdownurl+s.getSavedir()+"/"+s.getLogoHorizontal());
			obj.put("albmMessage", s.getAlbmMessage());
			obj.put("pices", s.getPices());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String dateString = formatter.format(s.getDate());
			obj.put("date", dateString);
			obj.put("index", s.getmId());
			jarr.add(obj);
			System.out.println(s.toString());
		}
		json.put("Album", jarr);
		str = json.toString();
		return str;
	}
	
	public static String ScanAlbumMusicList(int index){
		String str = "";
		Set<MusicListUtil>list_e =XiaomanyaoMusicSql.ScanMusicListByAlbumId(index);
		if(list_e==null){
			return str;
		}
		String downurl = ConfigServer.getInstance().getDownUrl();
		String httpsdownurl = ConfigServer.getInstance().getHttpsDownUrl();
		Iterator<MusicListUtil> iterator_e = list_e.iterator();
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		while(iterator_e.hasNext()){
			MusicListUtil s = (MusicListUtil) iterator_e.next();
			JSONObject obj = new JSONObject();
			obj.put("musicname", s.getMusicName());
			obj.put("url", downurl+s.getSaveDir()+"/"+s.getMusicName());
			obj.put("urls", httpsdownurl+s.getSaveDir()+"/"+s.getMusicName());
			obj.put("author", s.getAuthor());
			obj.put("md5", s.getMd5());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String dateString = formatter.format(s.getDate());
			obj.put("date", dateString);
			jarr.add(obj);
			System.out.println(s.toString());
		}
		json.put("musiclist", jarr);
		str = json.toString();
		return str;
	}
	
	public static String loadMoreAlbum(int page){
		String str = "";
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();
		json.put("Album", jarr);
		str = json.toString();
		return str;
	}
}

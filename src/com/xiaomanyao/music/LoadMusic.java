package com.xiaomanyao.music;

import com.hibernate.db.HibernateUtil;
import com.lanbao.common.Common;
import com.start.server.ConfigServer;

public class LoadMusic {

	public static void loadmusicfromMysql(String savePath){
//		String downUrl ="http://localhost:9000/XiaoManyao/music?method=down&fileName=";
		String str = XiaomanMusicAppInterface.ScanAlbumList();
		Common.WriteStrToFile(savePath, str);
	}   
}

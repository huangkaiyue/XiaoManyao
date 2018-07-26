package com.xiaomanyao.music;

import com.hibernate.db.HibernateUtil;
import com.lanbao.common.Common;

public class LoadMusic {

	public static void loadmusicfromMysql(String savePath){
//		String downUrl ="http://localhost:9000/XiaoManyao/music?method=down&fileName=";
		String downUrl = HibernateUtil.getConfiguration().getProperties().getProperty("hibernate.connection.downurl");
		String str = XiaomanMusicAppInterface.ScanAlbumList(downUrl);
		Common.WriteStrToFile(savePath, str);
	}   
}

package com.image.version;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hibernate.db.HiberSql;
import com.hibernate.db.HibernateUtil;
import com.hibernate.db.Hversion;
import com.hibernate.db.MusicListUtil;
import com.lanbao.common.Logutils;

public class XmyVerSql {
	public static int InsertImageVersion(String name,String md5,String message){
		int ret =-1;
		Hversion ver = new Hversion();
		ver.setName(name);
		ver.setMd5(md5);
		ver.setMessage(message);
		Date day=new Date();    
		ver.setDate(day);
		try {
			ver.toString();
			HiberSql.Insert(ver);
			ret=0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logutils.e("insert data failed");
		}
		return ret;
	}
	public static Hversion ScanVersion(){
		List<Object> list =null;
		Hversion hversion =null;
		String hql="from Hversion order by id desc ";
		Session session = HibernateUtil.getSession();
		Transaction tran = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(1);
		list=query.list();
		Iterator iterator_e = list.iterator();
		while(iterator_e.hasNext()){
			hversion  = (Hversion)iterator_e.next();
			System.out.println(hversion.toString());
			break;
		}
		return hversion;
	}
}

package com.lanbao.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.start.server.ConfigServer;

public class Logutils extends HttpServlet{

	private static  boolean writeLog(String filePath, String str) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time= df.format(new Date());
		System.out.println(time+":"+str);
		try {
			File tofile = new File(filePath);
			FileWriter fw = new FileWriter(tofile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.println(time+":"+str);
			pw.close();
			bw.close();
			fw.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	public static  boolean e(String str) {
		try {
			Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String path = ConfigServer.getInstance().getLogPath();
		return writeLog(path+"/log.txt",str);
	}
}

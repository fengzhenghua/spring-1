package com.tydic.unicom.crm.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ApplicationStartListener extends HttpServlet{
	@Override
	public void init() throws ServletException {
		try {
			GlobalConfig globalConfig = GlobalConfig.newInstance();
			String url = this.getClass().getResource("").toString();
			url = url.replaceAll("\\\\", "/");
			String subUrl = url.substring(0, url.lastIndexOf("/WEB-INF"));
			String subUrl1 = subUrl.substring(0, subUrl.lastIndexOf("/"));
			String subUrl2 = subUrl1.substring(0, subUrl1.lastIndexOf("/"));
			String suburl3 = subUrl2.substring(subUrl2.indexOf("/"), subUrl2.length());
			String filePath = suburl3+"/conf/CasAppConfig.properties";
			File file = new File(filePath);
			InputStream in = new FileInputStream(file);
			//InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("/appconfig.properties");
			globalConfig.initDataMap(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.init();
	}
}

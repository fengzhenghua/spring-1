package com.tydic.unicom.crm.web.uss.listener;

import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ApplicationStartListener extends HttpServlet{
	@Override
	public void init() throws ServletException {
		try {
			GlobalConfig globalConfig = GlobalConfig.newInstance();
			InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream("/appconfig.properties");
			globalConfig.initDataMap(resourceStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.init();
	}
}

package com.tydic.unicom.apc.common.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

/**
 * 获取spring信息的工具类
 * 
 * @author Administrator
 * 
 */
@Service("ToolSpring")
public final class ToolSpring extends ApplicationObjectSupport {
	private static ApplicationContext applicationContext = null;
	private static Logger logger = Logger.getLogger(ToolSpring.class);
	@Override
	protected void initApplicationContext(ApplicationContext context) throws BeansException {
		// TODO Auto-generated method stub
		super.initApplicationContext(context);
		if (ToolSpring.applicationContext == null) {
			ToolSpring.applicationContext = context;
		}
	}

	public static ApplicationContext getAppContext() {
		return applicationContext;
	}

	public static Object getBean(String name) {
		return getAppContext().getBean(name);
	}
}
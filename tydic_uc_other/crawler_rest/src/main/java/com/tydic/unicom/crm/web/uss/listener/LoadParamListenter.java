package com.tydic.unicom.crm.web.uss.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.tydic.unicom.crawler.business.interfaces.GetInfoServiceBus;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;

public class LoadParamListenter  implements ServletContextListener{

	private static Logger logger = Logger.getLogger(LoadParamListenter.class);
	
	private GetInfoServiceBus getInfoServiceBus;
				
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
		getInfoServiceBus = (GetInfoServiceBus) wac.getBean("GetInfoServiceBus");
		try {
			BusRespMessage result = getInfoServiceBus.initSysParamsMethod(null);
			logger.info("==========================爬虫系统初始化系统参数结果："+result.getContent());
		} catch (Exception e) {
			logger.error("=================初始化系统参数异常=================");
			e.printStackTrace();
		}
	}
	
}

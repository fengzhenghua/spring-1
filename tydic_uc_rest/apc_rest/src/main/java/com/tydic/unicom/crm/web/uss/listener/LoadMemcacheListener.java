/**
 * @ProjectName: uss_web
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年9月20日 下午2:49:40
 * @Title: LoadMemcacheListener.java
 * @Package com.tydic.unicom.crm.web.uss.listener
 * @Description: TODO
 */
package com.tydic.unicom.crm.web.uss.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tydic.unicom.service.cache.service.interfaces.MemInitServ;


/**
 * <p></p>
 * @author yangfei 2014年9月20日 下午2:49:40
 * @ClassName LoadMemcacheListener
 * @Description TODO 加载memcahce
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年9月20日
 * @modify by reason:{方法名}:{原因}
 */
public class LoadMemcacheListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(LoadMemcacheListener.class);

//	@Autowired
	private MemInitServ memInitServ;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
		memInitServ = (MemInitServ)wac.getBean("MemInitServ");
		logger.info("加载Memcache [code_type]");
		memInitServ.codeTypeInit();
		logger.info("加载Memcache [code_list]");
		memInitServ.codeListInit();
		logger.info("加载Memcache [code_list_map]");
		memInitServ.codeListMapInit();
	}
}

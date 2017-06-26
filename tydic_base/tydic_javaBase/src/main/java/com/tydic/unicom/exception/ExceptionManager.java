/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年10月14日 下午5:20:12
 * @Title: ExceptionManager.java
 * @Package com.tydic.unicom.exception
 * @Description: TODO
 */
package com.tydic.unicom.exception;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.tydic.unicom.util.XmlUtil;

/**
 * <p>
 * </p>
 * @author yangfei 2014年10月14日 下午5:20:12
 * @ClassName ExceptionManager
 * @Description TODO 异常管理类
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年10月14日
 * @modify by reason:{方法名}:{原因}
 */
public class ExceptionManager {
	
	private static Logger logger = Logger.getLogger(ExceptionManager.class);
	
	private static Map<String, BusinessException> exceptions = null;
	
	private static ExceptionManager exceptionManager = null;
	
	private ExceptionManager() {
	}
	
	public static ExceptionManager instance() {
		if (null == exceptionManager) {
			exceptionManager = new ExceptionManager();
		}
		return exceptionManager;
	}
	
	public void throwException(String system, String key) throws Exception {
		throw instance().exception(system + "_" + key);
	}
	
	public void throwException(String system, String key, String content) throws Exception {
		throw instance().exception(system + "_" + key, content);
	}
	
	private BusinessException exception(String key) throws Exception {
		if (null == exceptions) {
			loadBusinessException();
		}
		if (!exceptions.containsKey(key)) {
			return BusinessException.instance();
		}
		return exceptions.get(key);
	}
	
	private BusinessException exception(String key, String content) throws Exception {
		if (null == exceptions) {
			loadBusinessException();
		}
		if (!exceptions.containsKey(key)) {
			return BusinessException.instance();
		}
		BusinessException businessException = exceptions.get(key);
		businessException.setContent(content);
		return businessException;
	}
	
	private void loadBusinessException() throws Exception {
		logger.info("加载配置异常....");
		if (null == exceptions) {
			exceptions = new HashMap<String, BusinessException>();
		} else {
			return;
		}
		InputStream inputStream = this.getClass().getResourceAsStream("/config/exception/businessException.xml");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		/*XStream xStream = new XStream();
		xStream.processAnnotations(Exceptions.class);
		xStream.autodetectAnnotations(true);*/
		Exceptions e = (Exceptions) XmlUtil.parseObject(sb.toString(), Exceptions.class);
		for (BusinessException exception : e.getExceptions()) {
			exceptions.put(exception.getSystem() + "_" + exception.getKey(), exception);
		}
	}
}

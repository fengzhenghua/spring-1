package com.tydic.unicom.webUtil;

import org.apache.log4j.Logger;

public class UocUtils {

	private static Logger logger = Logger.getLogger(UocUtils.class);
	
	public boolean isEmpty(Object obj) throws Exception{
		if(obj == null){
			logger.info("==========空对象========");
			return false;
		}
		else{
			if((obj instanceof java.lang.String) && obj.equals("")){
				logger.info("==========空字符串========");
				return false;
			}
			if((obj instanceof java.util.List) && (((java.util.List) obj).isEmpty())){
				logger.info("==========空List========");
				return false;
			}
			if((obj instanceof java.util.Map) && (((java.util.Map)obj).isEmpty())){
				logger.info("==========空Map========");
				return false;
			}
			return true;
		}
	}
}

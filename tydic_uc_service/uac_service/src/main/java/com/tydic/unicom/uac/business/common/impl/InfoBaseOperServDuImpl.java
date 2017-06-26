package com.tydic.unicom.uac.business.common.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uac.business.common.interfaces.InfoBaseOperServDu;
import com.tydic.unicom.uac.service.common.interfaces.BaseOperServDu;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>
 * 认证中心服务
 * <p>
 * UAC0005-获取登录信息<br>
 * 
 * @author ZhangCheng
 * @version V1.0
 * @date 2017-03-02
 */
public class InfoBaseOperServDuImpl implements InfoBaseOperServDu {
	
	private static Logger logger = Logger.getLogger(InfoBaseOperServDuImpl.class);
	
	@Autowired
	private BaseOperServDu baseOperServDu;
	
	@Override
	public UocMessage getOperInfoByJsessionId(String jsessionId) throws Exception {
		
		logger.debug("DEBUG[UAC0005]==========>获取工号信息开始<==========");
		logger.info( "INFO [UAC0005]==========>请求参数：jsessionId：" + jsessionId);
		
		UocMessage respUocMessage = new UocMessage();
		
		respUocMessage = baseOperServDu.getOperInfoByJsessionId(jsessionId);
		
		logger.info( "INFO [UAC0005]==========>响应参数：respCode=" + respUocMessage.getRespCode() + ", content=" + respUocMessage.getContent() );
		logger.debug("DEBUG[UAC0005]==========>获取工号信息结束<==========");
		
		return respUocMessage;	
	}

}

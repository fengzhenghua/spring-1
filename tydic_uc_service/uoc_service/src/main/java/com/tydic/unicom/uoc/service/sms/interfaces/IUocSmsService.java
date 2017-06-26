package com.tydic.unicom.uoc.service.sms.interfaces;

import com.tydic.unicom.sms.ISmsService;
import com.tydic.unicom.webUtil.BusiMessage;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年5月31日 上午10:48:53
 * @ClassName IUocSmsService
 * @Description uoc短信服务,提供发送和备份功能
 * @version V1.0
 */
public interface IUocSmsService extends ISmsService {
	
	/**
	 * 
	 * @author heguoyong 2017年5月31日 上午10:51:10
	 * @Method: saveSmsHistory 
	 * @Description: 备份短信记录
	 */
	BusiMessage<?> saveSmsHistory(String smsId);
}

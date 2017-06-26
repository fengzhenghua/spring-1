package com.tydic.unicom.sms;

import com.tydic.unicom.webUtil.BusiMessage;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月24日 下午6:44:40
 * @ClassName ISmsService
 * @Description 短信发送接口
 * @version V1.0
 */
public interface ISmsService {
	
	/**
	 * 发送短信
	 * 
	 * @param msg
	 * @return
	 */
	public BusiMessage<String> sendShortMessage(ShortMessage<?> msg);
}

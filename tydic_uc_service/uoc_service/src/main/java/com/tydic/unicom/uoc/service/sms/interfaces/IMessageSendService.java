package com.tydic.unicom.uoc.service.sms.interfaces;

import com.tydic.unicom.sms.ShortMessage;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年5月31日 上午11:02:10
 * @ClassName IMessageSendService
 * @Description 发送短信服务
 * @version V1.0
 */
public interface IMessageSendService {
	/**
	 * 实际发送短信
	 * 
	 * @param msg
	 */
	public boolean sendShortMessage(ShortMessage<?> msg);
}

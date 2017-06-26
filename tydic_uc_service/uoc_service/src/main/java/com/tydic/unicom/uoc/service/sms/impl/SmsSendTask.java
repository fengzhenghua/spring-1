package com.tydic.unicom.uoc.service.sms.impl;

import com.tydic.unicom.sms.ShortMessage;
import com.tydic.unicom.uoc.service.sms.interfaces.IMessageSendService;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月31日 上午11:45:02
 * @ClassName SmsSendTask
 * @Description 短信发送任务
 * @version V1.0
 */
public class SmsSendTask implements Runnable {
	
	private ShortMessage<?> shortMsg;
	
	private IMessageSendService messageSendService;
	
	public void setMessageSendService(IMessageSendService messageSendService) {
		this.messageSendService = messageSendService;
	}
	
	/**
	 * 短信
	 * 
	 * @return
	 */
	public ShortMessage<?> getShortMsg() {
		return shortMsg;
	}
	
	/**
	 * 短信
	 * 
	 * @param shortMsg
	 */
	public void setShortMsg(ShortMessage<?> shortMsg) {
		this.shortMsg = shortMsg;
	}
	
	@Override
	public void run() {
		if (messageSendService != null) {
			messageSendService.sendShortMessage(shortMsg);
		}
	}
	
}

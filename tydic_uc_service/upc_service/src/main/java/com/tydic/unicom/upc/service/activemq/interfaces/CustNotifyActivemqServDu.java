package com.tydic.unicom.upc.service.activemq.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface CustNotifyActivemqServDu {

	
	
	/**
	 * 发送消息
	 * @param message
	 */
	public int sendMessage(UocMessage message);
}

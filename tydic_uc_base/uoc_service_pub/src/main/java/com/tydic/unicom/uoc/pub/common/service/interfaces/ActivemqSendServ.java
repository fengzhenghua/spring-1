package com.tydic.unicom.uoc.pub.common.service.interfaces;

import com.tydic.unicom.uoc.pub.common.service.po.ActivemqSendPo;
import com.tydic.unicom.webUtil.UocMessage;

public interface ActivemqSendServ {

	/**
	 * 向消息队列发送信息
	 * */
	public UocMessage SendMessage(ActivemqSendPo activemqSendPo,String queueName) throws Exception;
}

package com.tydic.unicom.uoc.service.common.interfaces;

import com.tydic.unicom.uoc.pub.common.service.po.ActivemqSendPo;
import com.tydic.unicom.webUtil.UocMessage;

public interface ActivemqSendServDu {
	/**
	 *     BASE0009  写消息队列中间表服务
	 * */
	public UocMessage createMessageQueue(ActivemqSendPo activemqSendPo,String queue_id) throws Exception;
	/**
	 * 
	 * 向消息队列发送信息  BASE0033
	 */
	public UocMessage sendMessage(ActivemqSendPo activemqSendPo,String queue_id) throws Exception;
}

package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface ActivemqServDu {


	/**
	 * 101	消息队列发送 UOC0072
	 * @throws Exception
	 */
	public UocMessage createSendActivemq(ParaVo vo) throws Exception;
}

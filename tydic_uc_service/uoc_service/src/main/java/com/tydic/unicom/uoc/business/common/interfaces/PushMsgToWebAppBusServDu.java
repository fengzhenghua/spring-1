package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface PushMsgToWebAppBusServDu {
	/**
	 * 将推送前台的消息保存到redis
	 * */
	public UocMessage pushMsgToWebAppByRedis(String operNo,String msgTpye) throws Exception;
}

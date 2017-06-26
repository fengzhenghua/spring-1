package com.tydic.unicom.uoc.business.common.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.business.common.interfaces.PushMsgToWebAppBusServDu;
import com.tydic.unicom.uoc.service.common.interfaces.PushMsgToWebAppServDu;
import com.tydic.unicom.webUtil.UocMessage;

public class PushMsgToWebAppBusServDuImpl implements PushMsgToWebAppBusServDu{
	@Autowired
	private PushMsgToWebAppServDu pushMsgToWebAppServDu;

	@Override
	public UocMessage pushMsgToWebAppByRedis(String operNo, String msgTpye) throws Exception {
		UocMessage uocMsg=new UocMessage();
		uocMsg=pushMsgToWebAppServDu.pushMsgToWebAppByRedis(operNo, msgTpye);
		return uocMsg;
	}

}

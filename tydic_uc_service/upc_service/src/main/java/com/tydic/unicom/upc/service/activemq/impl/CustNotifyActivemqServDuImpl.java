package com.tydic.unicom.upc.service.activemq.impl;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.service.activemq.interfaces.CustNotifyActivemqServDu;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 处理异步通知接口的消息
 * @author 吴川
 *
 */
public class CustNotifyActivemqServDuImpl extends BaseServImpl<UocMessage> implements CustNotifyActivemqServDu {

	//private static final Logger logger = Logger.getLogger(CustNotifyActivemqServDuImpl.class);

	@Override
	public int sendMessage(UocMessage message) {
		return S.get(UocMessage.class).batch(Condition.empty().filter("destination", "CustNotifyQueue"), message);
	}
}

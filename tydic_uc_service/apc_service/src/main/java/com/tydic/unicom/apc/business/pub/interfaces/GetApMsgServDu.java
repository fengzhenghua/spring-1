package com.tydic.unicom.apc.business.pub.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface GetApMsgServDu {
	/**
	 * APC0001  获取触点信息
	 * @param ap_id
	 * @return
	 * @throws Exception
	 */

	 
	public UocMessage queryApMsg(String ap_id) throws Exception;

}

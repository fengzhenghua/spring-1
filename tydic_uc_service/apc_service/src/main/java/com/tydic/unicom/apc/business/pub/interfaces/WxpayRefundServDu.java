package com.tydic.unicom.apc.business.pub.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface WxpayRefundServDu {

	/**
	 * 微信退款
	 * @param jsession_id
	 * @param serv_order_no
	 * @return
	 * @throws Exception
	 */
	public UocMessage createWxpayRefund(String jsession_id, String serv_order_no);
}

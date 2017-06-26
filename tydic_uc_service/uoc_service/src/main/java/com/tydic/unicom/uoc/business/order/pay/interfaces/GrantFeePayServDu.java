package com.tydic.unicom.uoc.business.order.pay.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface GrantFeePayServDu {

	/**
	 * AOP赠款 UOC0080
	 * @param jsession_id
	 * @param serv_order_no
	 * @param prom_fee
	 * @param acc_nbr
	 * @return
	 */
	public UocMessage promFeeForAop(String jsession_id, String serv_order_no, String prom_fee, String acc_nbr) throws Exception;
}

package com.tydic.unicom.uoc.business.order.service.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface OrderDataBakServDu {
	/**
	 * UOC数据备份 BASE0010
	 * @param order_no
	 * @param oper_type
	 * @return
	 * @throws Exception
	 */
	public UocMessage createOrderDataBakup(String order_no,String oper_type)throws Exception;

}

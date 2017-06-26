package com.tydic.unicom.uoc.business.order.service.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface OrderEnquiryContactServDu {
	
	/**
	 * uoc0073服务订单详情查询（触点专用）
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage queryOrderEnquiryContact(String jsession_id,String  serv_order_no) throws Exception;

}

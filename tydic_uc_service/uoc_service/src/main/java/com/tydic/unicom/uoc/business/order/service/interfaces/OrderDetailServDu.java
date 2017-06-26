package com.tydic.unicom.uoc.business.order.service.interfaces;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface OrderDetailServDu {
	/*
	 * 订单详情查询 UOC0010
	 */
	public UocMessage getOrderDetail(ParaVo paraVo) throws Exception;
}

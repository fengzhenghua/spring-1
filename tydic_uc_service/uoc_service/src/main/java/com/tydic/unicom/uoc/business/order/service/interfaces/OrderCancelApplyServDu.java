package com.tydic.unicom.uoc.business.order.service.interfaces;

import com.tydic.unicom.webUtil.UocMessage;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.vo.OrderCancelApplyVo;
public interface OrderCancelApplyServDu {
	
	/**
	 * 订单撤销申请
	 */
	public UocMessage createOrderCancelApply(OrderCancelApplyVo Vo) throws Exception;
	
	/**
	 * UOC0058 订单撤销审核
	 * @param paraVo jsession_id,order_no,order_type,audit_flag
	 * @return
	 * @throws Exception
	 */
	public UocMessage checkInfoOrderCancel(ParaVo paraVo) throws Exception;
}

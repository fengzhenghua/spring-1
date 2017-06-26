package com.tydic.unicom.uoc.business.order.sale.interfaces;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface PreOrderServDu {
	/**
	 * ⦁	预订单处理   UOC0033
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage preOrderDeal(ParaVo vo) throws Exception;
	
	/**
	 * 预订单流程启动 UOC0025
	 * @param paraVo
	 * @return
	 */
	
	public UocMessage preOrderStart(ParaVo paraVo) throws Exception;
}

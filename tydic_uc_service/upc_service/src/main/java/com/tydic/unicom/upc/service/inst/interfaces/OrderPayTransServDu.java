package com.tydic.unicom.upc.service.inst.interfaces;

import com.tydic.unicom.upc.vo.inst.OrderPayTransVo;

public interface OrderPayTransServDu {

	/**
	 * 新增
	 * @param orderPayTransPo
	 * @return
	 */
	public String addOrderPayTrans(OrderPayTransVo orderPayTransVo);
	
	/**
	 * 更新
	 * @param orderPayTransPo
	 * @return
	 */
	public boolean updateOrderPayTrans(OrderPayTransVo orderPayTransVo);
	
	/**
	 * 根据pay_order_id查询数据
	 * @param orderPayTransPo
	 * @return
	 */
	public OrderPayTransVo queryByPayOrderId(OrderPayTransVo orderPayTransVo);
}

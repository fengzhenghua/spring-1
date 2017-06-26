package com.tydic.unicom.upc.base.database.inst.interfaces;

import com.tydic.unicom.upc.base.database.po.inst.OrderPayTransPo;

public interface OrderPayTransServ {

	/**
	 * 新增
	 * @param orderPayTransPo
	 * @return
	 */
	public boolean addOrderPayTrans(OrderPayTransPo orderPayTransPo);
	
	/**
	 * 更新
	 * @param orderPayTransPo
	 * @return
	 */
	public boolean updateOrderPayTrans(OrderPayTransPo orderPayTransPo);
	
	/**
	 * 根据pay_order_id查询数据
	 * @param orderPayTransPo
	 * @return
	 */
	public OrderPayTransPo queryByPayOrderId(OrderPayTransPo orderPayTransPo);
	
	
}

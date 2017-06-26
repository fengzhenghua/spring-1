package com.tydic.unicom.uoc.service.task.interfaces;


import com.tydic.unicom.uoc.business.order.sale.vo.SaleOrderInVo;
import com.tydic.unicom.webUtil.UocMessage;



public interface QueryNotComSalesOrderListServDu {
	
	/**
	 * 查询未竣工的销售订单列表
	 * @param vo
	 * @return
	 */
	public UocMessage queryNotComSalesOrderList(SaleOrderInVo vo)throws Exception;
	
	/**
	 * 查询未竣工的服务订单列表
	 * @param vo
	 * @return
	 */
	public UocMessage queryNotComServOrderList(SaleOrderInVo vo)throws Exception;

}

package com.tydic.unicom.uoc.business.order.sale.interfaces;

import com.tydic.unicom.uoc.business.order.sale.vo.SaleOrderInVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface QuerySalesOrderListServDu {
	
	/**
	 * 销售订单列表查询
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage SalesOrderList(SaleOrderInVo vo)throws Exception;
	
	/**
	 * 服务订单列表查询
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public UocMessage ServOrderList(SaleOrderInVo vo)throws Exception;

}

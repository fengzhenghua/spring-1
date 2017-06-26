package com.tydic.unicom.uoc.service.task.interfaces;


import com.tydic.unicom.uoc.business.order.sale.vo.SaleOrderInVo;
import com.tydic.unicom.webUtil.UocMessage;



public interface QueryBeComSalesOrderListServDu {
	
	/**
	 * 查询竣工的销售订单列表
	 * @param vo
	 * @return
	 */
	public UocMessage queryBeComSalesOrderList(SaleOrderInVo vo)throws Exception;
	
	/**
	 * 查询竣工的服务订单列表
	 * @param vo
	 * @return
	 */
	public UocMessage queryBeComServOrderList(SaleOrderInVo vo)throws Exception;

}

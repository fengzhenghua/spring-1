package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderOfrMapVo;

public interface InfoSaleOrderOfrMapServDu {

	/*
	 * 根据SaleOrderNo查询销售订单商品表
	 */
	public List<InfoSaleOrderOfrMapVo> queryInfoSaleOrderOfrMapBySaleOrderNo(InfoSaleOrderOfrMapVo vo)throws Exception;
	/*
	 * 根据SaleOrderNo删除InfoSaleOrderOfrMap表
	 */
	public boolean deleteInfoSaleOrderOfrMapBySaleOrderNo(InfoSaleOrderOfrMapVo vo)throws Exception;
}

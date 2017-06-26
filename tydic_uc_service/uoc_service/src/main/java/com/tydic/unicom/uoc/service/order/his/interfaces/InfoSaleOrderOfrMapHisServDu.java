package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderOfrMapHisVo;

public interface InfoSaleOrderOfrMapHisServDu {

	/*
	 * 根据SaleOrderNo查询销售订单商品表
	 */
	public List<InfoSaleOrderOfrMapHisVo> queryInfoSaleOrderOfrMapHisBySaleOrderNo(InfoSaleOrderOfrMapHisVo vo)throws Exception;
	
}

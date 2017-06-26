package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderServMapHisVo;

public interface InfoSaleOrderServMapHisServDu {

	/*
	 * 根据SaleOrderNo查询销售订单业务表
	 */
	public List<InfoSaleOrderServMapHisVo> queryInfoSaleOrderServMapHisBySaleOrderNo(InfoSaleOrderServMapHisVo vo)throws Exception;
	
}

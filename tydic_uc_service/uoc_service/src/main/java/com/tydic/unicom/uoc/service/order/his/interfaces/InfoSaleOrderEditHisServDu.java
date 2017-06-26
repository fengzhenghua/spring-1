package com.tydic.unicom.uoc.service.order.his.interfaces;


import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderEditHisVo;

public interface InfoSaleOrderEditHisServDu {

	/*
	 * 根据SaleOrderNo获取销售订单修订表
	 */
	public InfoSaleOrderEditHisVo getInfoSaleOrderEditBySaleOrderNo(InfoSaleOrderEditHisVo vo)throws Exception;
	
}

package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderPersionHisPo;

public interface InfoSaleOrderPersionHisServ {
	
	/*
	 * 写入销售订单个客信息表历史表
	 */
	public boolean createInfoSaleOrderPersionHisPo(InfoSaleOrderPersionHisPo po) throws Exception;

	public InfoSaleOrderPersionHisPo queryInfoSaleOrderPersionHisBySaleOrderNo(InfoSaleOrderPersionHisPo po) throws Exception;
}

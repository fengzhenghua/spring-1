package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderEditHisPo;

public interface InfoSaleOrderEditHisServ {
	
	/*
	 * 写入销售订单修订表历史表
	 */
	public boolean createInfoSaleOrderEditHisPo(InfoSaleOrderEditHisPo po)throws Exception;

	public List<InfoSaleOrderEditHisPo> queryInfoSaleOrderEditHisByOrderNo(InfoSaleOrderEditHisPo po)throws Exception;
}

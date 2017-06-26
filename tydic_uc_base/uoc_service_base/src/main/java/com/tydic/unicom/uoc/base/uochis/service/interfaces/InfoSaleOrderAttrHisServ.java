package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderAttrHisPo;

public interface InfoSaleOrderAttrHisServ {
	
	/*
	 * 写入销售订单属性集表历史表
	 */
	public boolean createInfoSaleOrderAttrHisPo(InfoSaleOrderAttrHisPo po)throws Exception;

	public List<InfoSaleOrderAttrHisPo> queryInfoSaleOrderAttrHisByOrderNo(InfoSaleOrderAttrHisPo po)throws Exception;

}

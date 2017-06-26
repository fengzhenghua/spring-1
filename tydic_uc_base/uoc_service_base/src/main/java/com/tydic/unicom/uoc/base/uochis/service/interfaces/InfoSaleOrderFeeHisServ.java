package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderFeeHisPo;

public interface InfoSaleOrderFeeHisServ {
	
	/*
	 * 写入销售订单费用信息表历史表
	 */
	public boolean createInfoSaleOrderFeeHisPo(InfoSaleOrderFeeHisPo po)throws Exception;

	public List<InfoSaleOrderFeeHisPo> queryInfoSaleOrderFeeHisBySaleOrderNo(InfoSaleOrderFeeHisPo po)throws Exception;
}

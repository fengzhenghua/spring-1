package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderDistributionHisPo;

public interface InfoSaleOrderDistributionHisServ {
	
	/*
	 * 写入销售订单收件人信息表历史表
	 */
	public boolean createInfoSaleOrderDistributionHisPo(InfoSaleOrderDistributionHisPo po) throws Exception;

	public List<InfoSaleOrderDistributionHisPo> queryInfoSaleOrderDistributionHisByOrderNo(InfoSaleOrderDistributionHisPo po) throws Exception;
}

package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderDistrDetailHisPo;

public interface InfoSaleOrderDistrDetailHisServ {
	
	/*
	 * 写入销售订单包裹信息表历史表
	 */
	public boolean createInfoSaleOrderDistrDetailHisPo(InfoSaleOrderDistrDetailHisPo po) throws Exception;

	public List<InfoSaleOrderDistrDetailHisPo> queryInfoSaleOrderDistrDetailHisByOrderNo(InfoSaleOrderDistrDetailHisPo po) throws Exception;

}

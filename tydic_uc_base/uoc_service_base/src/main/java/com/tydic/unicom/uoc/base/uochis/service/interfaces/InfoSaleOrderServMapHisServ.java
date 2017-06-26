package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderServMapHisPo;

public interface InfoSaleOrderServMapHisServ {
	/**
	 * 写销售订单业务表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoSaleOrderServMapHisPo(InfoSaleOrderServMapHisPo po)throws Exception;


	public List<InfoSaleOrderServMapHisPo> queryInfoSaleOrderServMapHisBySaleOrderNo(InfoSaleOrderServMapHisPo po)throws Exception;
	
	public List<InfoSaleOrderServMapHisPo> queryInfoSaleOrderServMapHisByAccNbr(InfoSaleOrderServMapHisPo po)throws Exception;
}

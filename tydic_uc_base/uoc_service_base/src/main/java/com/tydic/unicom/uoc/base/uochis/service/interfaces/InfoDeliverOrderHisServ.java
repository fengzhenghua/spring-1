package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoDeliverOrderHisPo;

public interface InfoDeliverOrderHisServ {
	
	/**
	 * 写交付订单表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoDeliverOrderHisPo(InfoDeliverOrderHisPo po)throws Exception;

	public List<InfoDeliverOrderHisPo> queryInfoDeliverOrderHisByOrderNo(InfoDeliverOrderHisPo po)throws Exception;
	
	public List<InfoDeliverOrderHisPo> queryInfoDeliverOrderHisAll(InfoDeliverOrderHisPo po) throws Exception;
}

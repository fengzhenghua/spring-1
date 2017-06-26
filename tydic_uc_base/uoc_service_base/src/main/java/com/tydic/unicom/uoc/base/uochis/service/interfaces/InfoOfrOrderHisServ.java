package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderHisPo;

public interface InfoOfrOrderHisServ {
	
	
	/**
	 * 写入商品订单表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoOfrOrderHisPo(InfoOfrOrderHisPo po)throws Exception;
	
	public List<InfoOfrOrderHisPo> queryInfoOfrOrderHisByOrderNo(InfoOfrOrderHisPo po)throws Exception;

}

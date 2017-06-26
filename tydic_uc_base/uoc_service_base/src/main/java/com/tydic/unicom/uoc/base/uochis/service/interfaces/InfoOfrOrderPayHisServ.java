package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderPayHisPo;

public interface InfoOfrOrderPayHisServ {
	
	/**
	 * 写入商品订单收费详情表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoOfrOrderPayHisPo(InfoOfrOrderPayHisPo po)throws Exception;

	public List<InfoOfrOrderPayHisPo> queryInfoOfrOrderPayHisByOrderNo(InfoOfrOrderPayHisPo po)throws Exception;
}

package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderPersionHisPo;

public interface InfoServiceOrderPersionHisServ {
	
	/**
	 * 写服务订单个客信息表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoSaleOrderAttrHisPo(InfoServiceOrderPersionHisPo po)throws Exception;

	public InfoServiceOrderPersionHisPo queryInfoServiceOrderPersionHisByOrderNo(InfoServiceOrderPersionHisPo po)throws Exception;
}

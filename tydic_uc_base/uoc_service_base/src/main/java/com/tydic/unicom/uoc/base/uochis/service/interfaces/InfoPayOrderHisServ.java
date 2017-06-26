package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoPayOrderHisPo;

public interface InfoPayOrderHisServ {
	/**
	 * 写支付订单表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoPayOrderHisPo(InfoPayOrderHisPo po)throws Exception;

	public List<InfoPayOrderHisPo> queryInfoPayOrderHisByOrderNo(InfoPayOrderHisPo po)throws Exception;
	
	/**
	 * 根据支付类型查询支付订单表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoPayOrderHisPo> queryInfoPayOrderHisByPayType(InfoPayOrderHisPo po) throws Exception;
}

package com.tydic.unicom.uoc.service.order.interfaces;

import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderPersionVo;

public interface InfoSaleOrderPersionServDu {

	/*
	 * 根据SaleOrderNo删除InfoSaleOrderPersion表
	 */
	public boolean deleteInfoSaleOrderPersionBySaleOrderNo(InfoSaleOrderPersionVo vo)throws Exception;
	
	public InfoSaleOrderPersionVo getInfoSaleOrderPersionBySaleOrderNo(InfoSaleOrderPersionVo vo)throws Exception;
}

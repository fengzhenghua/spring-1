package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderEditVo;

public interface InfoSaleOrderEditServDu {

	/*
	 * 根据SaleOrderNo获取销售订单修订表
	 */
	public InfoSaleOrderEditVo getInfoSaleOrderEditBySaleOrderNo(InfoSaleOrderEditVo vo)throws Exception;
	/*
	 * 写入销售订单修订表
	 */
	public boolean createInfoSaleOrderEdit(InfoSaleOrderEditVo vo)throws Exception;
	
	public List<InfoSaleOrderEditVo> queryInfoSaleOrderEditBySaleOrderNo(InfoSaleOrderEditVo vo)throws Exception;
}

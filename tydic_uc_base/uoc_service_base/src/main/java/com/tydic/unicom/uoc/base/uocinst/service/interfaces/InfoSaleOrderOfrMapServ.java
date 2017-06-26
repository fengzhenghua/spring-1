package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderOfrMapPo;

public interface InfoSaleOrderOfrMapServ {
	/*
	 * 根据SaleOrderNo查询销售订单商品表
	 */
	public List<InfoSaleOrderOfrMapPo> queryInfoSaleOrderOfrMapBySaleOrderNo(InfoSaleOrderOfrMapPo po)throws Exception;
	/*
	 * 根据SaleOrderNo删除InfoSaleOrderOfrMap表
	 */
	public boolean deleteInfoSaleOrderOfrMapBySaleOrderNo(InfoSaleOrderOfrMapPo po)throws Exception;
	
	/*
	 * 根据acc_nbr查询销售订单商品表
	 */
	public List<InfoSaleOrderOfrMapPo> queryInfoSaleOrderOfrMapByAccNbr(InfoSaleOrderOfrMapPo po)throws Exception;
}

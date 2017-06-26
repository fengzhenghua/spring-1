package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderServMapPo;

public interface InfoSaleOrderServMapServ {
	/*
	 * 根据SaleOrderNo查询销售订单业务表
	 */
	public List<InfoSaleOrderServMapPo> queryInfoSaleOrderServMapBySaleOrderNo(InfoSaleOrderServMapPo po)throws Exception;

	/*
	 * 根据SaleOrderNo删除InfoSaleOrderServMap表
	 */
	public boolean deleteInfoSaleOrderServMapBySaleOrderNo(InfoSaleOrderServMapPo po)throws Exception;
	
	/*
	 * 根据AccNbr查询销售订单业务表
	 */
	public List<InfoSaleOrderServMapPo> queryInfoSaleOrderServMapByAccNbr(InfoSaleOrderServMapPo po)throws Exception;

	public boolean createInfoSaleOrderServMap(InfoSaleOrderServMapPo po) throws Exception;
}

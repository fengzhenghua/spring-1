package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderExtPo;

public interface InfoSaleOrderExtServ {

	/*
	 * 根据SaleOrderNo删除InfoSaleOrderDistribution表
	 */
	public boolean deleteInfoSaleOrderExtBySaleOrderNo(InfoSaleOrderExtPo po)throws Exception;
	/**
	 * 根据销售订单号查询销售订单拓展属性横表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public InfoSaleOrderExtPo getInfoSaleOrderExtBySaleOrderNo(InfoSaleOrderExtPo po)throws Exception;
	
}

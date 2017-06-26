package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionPo;

public interface InfoSaleOrderPersionServ {
	/*
	 * 根据SaleOrderNo删除InfoSaleOrderPersion表
	 */
	public boolean deleteInfoSaleOrderPersionBySaleOrderNo(InfoSaleOrderPersionPo po)throws Exception;
	
	/**
	 * 根据销售订单号查询销售订单个客信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public InfoSaleOrderPersionPo getInfoSaleOrderPersionBySaleOrderNo(InfoSaleOrderPersionPo po)throws Exception;
}

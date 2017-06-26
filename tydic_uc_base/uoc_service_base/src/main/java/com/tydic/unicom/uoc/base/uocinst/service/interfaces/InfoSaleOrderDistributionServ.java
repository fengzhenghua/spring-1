package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderDistributionPo;

public interface InfoSaleOrderDistributionServ {
	/*
	 * 根据SaleOrderNo删除InfoSaleOrderDistribution表
	 */
	public boolean deleteInfoSaleOrderDistributionBySaleOrderNo(InfoSaleOrderDistributionPo po)throws Exception;
	
	/**
	 * 根据销售订单号查询销售订单收件人信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public InfoSaleOrderDistributionPo getInfoSaleOrderDistributionBySaleOrderNo(InfoSaleOrderDistributionPo po)throws Exception;
	
	/**
	 * 根据支付订单号查询销售订单收件人信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoSaleOrderDistributionPo> queryInfoSaleOrderDistributionByPayOrderNo(InfoPayOrderPo po)throws Exception;
}

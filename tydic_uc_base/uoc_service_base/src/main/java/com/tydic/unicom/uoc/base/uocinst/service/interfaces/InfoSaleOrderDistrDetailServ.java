package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderDistrDetailPo;

public interface InfoSaleOrderDistrDetailServ {

	/*
	 * 根据SaleOrderNo删除InfoSaleOrderDistrDetail表
	 */
	public boolean deleteInfoSaleOrderDistrDetailBySaleOrderNo(InfoSaleOrderDistrDetailPo po)throws Exception;
	
	/**
	 * 根据销售订单号查询销售订单包裹信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoSaleOrderDistrDetailPo> queryInfoSaleOrderDistrDetailBySaleOrderNo(InfoSaleOrderDistrDetailPo po)throws Exception;
	
	/**
	 * 根据支付订单号查询销售订单包裹信息表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<InfoSaleOrderDistrDetailPo> queryInfoSaleOrderDistrDetailByPayOrderNo(InfoPayOrderPo po) throws Exception;
}

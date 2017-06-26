package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderAttrPo;

public interface InfoSaleOrderAttrServ {
	/*
	 * 根据SaleOrderNo删除InfoSaleOrderAttr表
	 */
	public boolean deleteInfoSaleOrderAttrBySaleOrderNo(InfoSaleOrderAttrPo po)throws Exception;
	
	/**
	 * 根据销售订单号查询销售订单属性集表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public List<InfoSaleOrderAttrPo> queryInfoSaleOrderAttrBySaleOrderNo(InfoSaleOrderAttrPo po)throws Exception;

}

package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPo;

public interface InfoSaleOrderServ {
	/*
	 * 根据SaleOrderNo查询销售订单表
	 */
	public InfoSaleOrderPo getInfoSaleOrderPoBySaleOrderNo(InfoSaleOrderPo po)throws Exception;
	/*
	 * 根据SaleOrderNo 或accept_no+accept_system 查询销售订单表
	 */
	public InfoSaleOrderPo queryInfoSaleOrderPo(InfoSaleOrderPo po)throws Exception;
	/*
	 * 修改销售订单表
	 */
	public boolean updateInfoSaleOrderPo(InfoSaleOrderPo po)throws Exception;
	/*
	 * 写入销售订单表
	 */
	public boolean createInfoSaleOrderPo(InfoSaleOrderPo po)throws Exception;
	
	
	/*
	 * 根据销售订单号删除销售订单表
	 */
	public boolean deleteInfoSaleOrderPo(InfoSaleOrderPo po)throws Exception;
	
	public List<InfoSaleOrderPo> queryInfoSaleOrderPoList(SaleOrderInPo po,int pageNo,int pageSize)throws Exception;
	
	public int queryInfoSaleOrderPoListCount(SaleOrderInPo po)throws Exception;
	
	public InfoSaleOrderPo queryInfoSaleOrder(SaleOrderInPo po)throws Exception;
	
	public List<InfoSaleOrderPo> queryInfoSaleOrderByOrderState(InfoSaleOrderPo po) throws Exception;
		
}

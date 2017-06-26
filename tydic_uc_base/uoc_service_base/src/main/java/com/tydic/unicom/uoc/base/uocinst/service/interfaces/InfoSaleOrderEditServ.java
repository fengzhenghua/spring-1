package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderEditPo;

public interface InfoSaleOrderEditServ {
	/*
	 * 根据SaleOrderNo获取销售订单修订表
	 */
	public InfoSaleOrderEditPo getInfoSaleOrderEditPoBySaleOrderNo(InfoSaleOrderEditPo po)throws Exception;
	/*
	 * 写入销售订单修订表
	 */
	public boolean createInfoSaleOrderEditPo(InfoSaleOrderEditPo po)throws Exception;
	
	/**
	 * 通过销售订单号删除记录
	 * @param po
	 * @return
	 */
	
	public boolean delete(InfoSaleOrderEditPo po);
	
	public List<InfoSaleOrderEditPo> queryInfoSaleOrderEditPoBySaleOrderNo(InfoSaleOrderEditPo po)throws Exception;
	
}

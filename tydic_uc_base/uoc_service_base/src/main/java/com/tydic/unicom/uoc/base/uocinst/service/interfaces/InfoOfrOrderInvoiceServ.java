package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderInvoicePo;

public interface InfoOfrOrderInvoiceServ {
	/**
	 * 根据订单号查询商品订单一次性费用发票表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public List<InfoOfrOrderInvoicePo> queryInfoOfrOrderInvoiceByOrderNo(InfoOfrOrderInvoicePo po) throws Exception;
	
	/**
	 * 根据销售订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean  deleteInfoOfrOrderInvoiceBySaleOrderNo(InfoOfrOrderInvoicePo po) throws Exception;

}

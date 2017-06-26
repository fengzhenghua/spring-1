package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoOfrOrderInvoiceHisPo;

public interface InfoOfrOrderInvoiceHisServ {
	
	/**
	 * 写入商品订单一次性费用发票表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean createInfoOfrOrderInvoiceHisPo(InfoOfrOrderInvoiceHisPo po)throws Exception;

	public List<InfoOfrOrderInvoiceHisPo> queryInfoOfrOrderInvoiceHisByOrderNo(InfoOfrOrderInvoiceHisPo po)throws Exception;

}

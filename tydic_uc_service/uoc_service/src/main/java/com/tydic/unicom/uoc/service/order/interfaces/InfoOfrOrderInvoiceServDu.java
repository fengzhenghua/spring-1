package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderInvoiceVo;

public interface InfoOfrOrderInvoiceServDu {

	public List<InfoOfrOrderInvoiceVo> queryInfoOfrOrderInvoiceByOrderNo(InfoOfrOrderInvoiceVo vo)throws Exception ;
}

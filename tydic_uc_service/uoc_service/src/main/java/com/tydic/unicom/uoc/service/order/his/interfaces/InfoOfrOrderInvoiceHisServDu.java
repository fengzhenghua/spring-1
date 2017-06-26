package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderInvoiceHisVo;

public interface InfoOfrOrderInvoiceHisServDu {

	public List<InfoOfrOrderInvoiceHisVo> queryInfoOfrOrderInvoiceByOrderNo(InfoOfrOrderInvoiceHisVo vo)throws Exception ;
}

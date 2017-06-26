package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderHisVo;

public interface InfoOfrOrderHisServDu {
	
	/*
	 * 通过销售订单查询订单下的所以商品订单（多条）
	 */
	public List<InfoOfrOrderHisVo> queryInfoOfrOrderHisBySaleOrderNo(InfoOfrOrderHisVo vo) throws Exception;
	
	public List<InfoOfrOrderHisVo> queryInfoOfrOrderHisByOrderNo(InfoOfrOrderHisVo vo) throws Exception;
	
}

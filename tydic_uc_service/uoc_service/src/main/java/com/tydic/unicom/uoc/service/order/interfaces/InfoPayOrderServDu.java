package com.tydic.unicom.uoc.service.order.interfaces;

import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;

public interface InfoPayOrderServDu {

	public InfoPayOrderVo getInfoPayOrderByPayOrderNo(InfoPayOrderVo vo) throws Exception;
	
	/*
	 * 根据订单号(销售订单号，商品订单号，服务订单号)rela_order_no ，和订单类型rela_order_type查支付订单表
	 */
	public InfoPayOrderVo queryInfoPayOrderByRelaOrderNo(InfoPayOrderVo vo) throws Exception;
	
	public boolean updateInfoPayOrder(InfoPayOrderVo vo) throws Exception;
	
	public boolean createInfoPayOrder(InfoPayOrderVo vo) throws Exception;
	
//	public List<InfoPayOrderVo> queryInfoPayOrderByRelaOrderNo(InfoPayOrderVo vo) throws Exception;
}

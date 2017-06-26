package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoPayOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderFeeHisVo;

public interface InfoSaleOrderFeeHisServDu {

	
	/*
	 * 按支付订单查  销售订单费用信息表
	 */
	public List<InfoSaleOrderFeeHisVo> queryInfoSaleOrderFeeHisByPayOrder(InfoPayOrderHisVo vo)throws Exception;
 
	public InfoSaleOrderFeeHisVo queryInfoSaleOrderFeeHisBySaleOrderNo(InfoSaleOrderFeeHisVo vo)throws Exception;
}

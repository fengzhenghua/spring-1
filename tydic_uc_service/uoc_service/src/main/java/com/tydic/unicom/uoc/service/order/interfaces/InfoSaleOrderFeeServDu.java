package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderFeeVo;

public interface InfoSaleOrderFeeServDu {

	/*
	 * 创建销售订单费用信息表
	 */
	public boolean createInfoSaleOrderFee(InfoSaleOrderFeeVo vo)throws Exception;
	
	/*
	 * 按支付订单查  销售订单费用信息表
	 */
	public List<InfoSaleOrderFeeVo> queryInfoSaleOrderFeeByPayOrder(InfoPayOrderVo vo)throws Exception;
 
	public InfoSaleOrderFeeVo queryInfoSaleOrderFeeBySaleOrderNo(InfoSaleOrderFeeVo vo)throws Exception;
}

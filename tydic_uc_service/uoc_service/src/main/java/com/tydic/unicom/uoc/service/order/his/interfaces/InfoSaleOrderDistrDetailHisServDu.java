package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoPayOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderDistrDetailHisVo;

public interface InfoSaleOrderDistrDetailHisServDu {

	
	
	public List<InfoSaleOrderDistrDetailHisVo> queryInfoSaleOrderDistrDetailHisBySaleOrderNo(InfoSaleOrderDistrDetailHisVo vo)throws Exception;

	/*
	 * 按交付订单查 销售订单包裹信息表数据
	 */
	public List<InfoSaleOrderDistrDetailHisVo> queryInfoSaleOrderDistrDetailHisByPayOrderNo(InfoPayOrderHisVo vo)throws Exception;
}

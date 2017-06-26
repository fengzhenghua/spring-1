package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderDistrDetailVo;

public interface InfoSaleOrderDistrDetailServDu {

	/*
	 * 根据SaleOrderNo删除InfoSaleOrderDistrDetail表
	 */
	public boolean deleteInfoSaleOrderDistrDetailBySaleOrderNo(InfoSaleOrderDistrDetailVo vo)throws Exception;
	
	
	public List<InfoSaleOrderDistrDetailVo> queryInfoSaleOrderDistrDetailBySaleOrderNo(InfoSaleOrderDistrDetailVo vo)throws Exception;

	/*
	 * 按交付订单查 销售订单包裹信息表数据
	 */
	public List<InfoSaleOrderDistrDetailVo> queryInfoSaleOrderDistrDetailByPayOrderNo(InfoPayOrderVo vo)throws Exception;
}

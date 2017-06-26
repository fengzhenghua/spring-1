package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderDistributionVo;

public interface InfoSaleOrderDistributionServDu {
	/*
	 * 根据SaleOrderNo删除InfoSaleOrderDistribution表
	 */
	public boolean deleteInfoSaleOrderDistributionBySaleOrderNo(InfoSaleOrderDistributionVo vo)throws Exception;
	
	public InfoSaleOrderDistributionVo getInfoSaleOrderDistributionBySaleOrderNo(InfoSaleOrderDistributionVo vo)throws Exception;
	
	public List<InfoSaleOrderDistributionVo> queryInfoSaleOrderDistributionByPayOrderNo(InfoPayOrderVo vo)throws Exception;

}

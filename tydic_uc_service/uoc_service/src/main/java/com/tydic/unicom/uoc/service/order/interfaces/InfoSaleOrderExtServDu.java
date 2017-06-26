package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.Map;

import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderExtVo;

public interface InfoSaleOrderExtServDu {
	/*
	 * 根据SaleOrderNo删除InfoSaleOrderDistribution表
	 */
	public boolean deleteInfoSaleOrderExtBySaleOrderNo(InfoSaleOrderExtVo vo)throws Exception;
	
	public InfoSaleOrderExtVo getInfoSaleOrderExtBySaleOrderNo(InfoSaleOrderExtVo vo)throws Exception;
	
	public Map<String, Object> getAttrDesc(InfoSaleOrderExtVo vo,String tableName)throws Exception ;
}

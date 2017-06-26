package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderServMapVo;

public interface InfoSaleOrderServMapServDu {

	/*
	 * 根据SaleOrderNo查询销售订单业务表
	 */
	public List<InfoSaleOrderServMapVo> queryInfoSaleOrderServMapBySaleOrderNo(InfoSaleOrderServMapVo vo)throws Exception;
	/*
	 * 根据SaleOrderNo删除InfoSaleOrderServMap表
	 */
	public boolean deleteInfoSaleOrderServMapBySaleOrderNo(InfoSaleOrderServMapVo vo)throws Exception;
	
	public boolean createInfoSaleOrderServMap(InfoSaleOrderServMapVo vo) throws Exception;
}

package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uochis.po.SalesOrderListHisPo;

public interface SalesOrderListHisServ {
	
	
	/**
	 * 根据传进来的参数关联查询 销售订单表历史表、销售订单个客信息表历史表、销售商品表历史表、销售业务表历史表,返回数据条数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int querySalesOrderListHisCount(SaleOrderInPo po)throws Exception;

	
	public List<SalesOrderListHisPo> querySalesOrderListHis(SaleOrderInPo po,int pageNo,int pageSize)throws Exception;
}

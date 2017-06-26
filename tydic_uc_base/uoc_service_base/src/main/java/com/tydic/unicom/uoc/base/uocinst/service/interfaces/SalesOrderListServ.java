package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uocinst.po.SalesOrderListPo;

public interface SalesOrderListServ {
	
	/**
	 * 根据传进来的参数关联查询 销售订单表、销售订单个客信息表、销售商品表、销售业务表,返回数据条数
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public int querySalesOrderListCount(SaleOrderInPo po)throws Exception;
	
	
	public List<SalesOrderListPo> querySalesOrderList(SaleOrderInPo po,int pageNo,int pageSize)throws Exception;
	

}

package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderHisPo;

public interface InfoSaleOrderHisServ {
	/**
	 * 写入销售订单表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoSaleOrderHisPo(InfoSaleOrderHisPo po)throws Exception;

	public InfoSaleOrderHisPo queryInfoSaleOrderHisBySaleOrderNo(InfoSaleOrderHisPo po)throws Exception;
	
    public List<InfoSaleOrderHisPo> queryInfoSaleOrderPoHisList(SaleOrderInPo po,int pageNo,int pageSize)throws Exception;
	
	public int queryInfoSaleOrderPoHisListCount(SaleOrderInPo po)throws Exception;
	
	public InfoSaleOrderHisPo queryInfoSaleOrderHis(SaleOrderInPo po)throws Exception;
}

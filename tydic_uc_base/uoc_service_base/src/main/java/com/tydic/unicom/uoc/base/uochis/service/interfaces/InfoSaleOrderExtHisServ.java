package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderExtHisPo;

public interface InfoSaleOrderExtHisServ {
	
	/**
	 * 写入销售订单拓展属性横表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean create(InfoSaleOrderExtHisPo po) throws Exception;

	public InfoSaleOrderExtHisPo queryInfoSaleOrderExtHisBySaleOrderNo(InfoSaleOrderExtHisPo po) throws Exception;
}

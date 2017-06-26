package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderAttrHisPo;

public interface InfoServiceOrderAttrHisServ {
	/**
	 * 写服务订单属性表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean createInfoSaleOrderAttrHisPo(InfoServiceOrderAttrHisPo po)throws Exception;
	
	public List<InfoServiceOrderAttrHisPo> queryInfoServiceOrderAttrHisByOrderNo(InfoServiceOrderAttrHisPo po)throws Exception;

}

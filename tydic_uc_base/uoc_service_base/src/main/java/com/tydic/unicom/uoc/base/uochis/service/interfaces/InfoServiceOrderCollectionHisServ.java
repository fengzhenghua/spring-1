package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderCollectionHisPo;

public interface InfoServiceOrderCollectionHisServ {
	/**
	 * 写服务订单银行托收表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoSaleOrderAttrHisPo(InfoServiceOrderCollectionHisPo po)throws Exception;

	public List<InfoServiceOrderCollectionHisPo> queryInfoServiceOrderCollectionHisByOrderNo(InfoServiceOrderCollectionHisPo po)throws Exception;
}

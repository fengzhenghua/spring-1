package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderProdMapHisPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderProdMapPo;

public interface InfoServiceOrderProdMapHisServ {
	/**
	 * 写服务订单产品列表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoServiceOrderProdMapHisPo(InfoServiceOrderProdMapHisPo po)throws Exception;

	public List<InfoServiceOrderProdMapHisPo> queryInfoServiceOrderProdMapHisByOrderNo(InfoServiceOrderProdMapHisPo po)throws Exception;
	
	public List<InfoServiceOrderProdMapHisPo> queryInfoServiceOrderProdMapHisByProdCode(InfoServiceOrderProdMapHisPo po)throws Exception;
}

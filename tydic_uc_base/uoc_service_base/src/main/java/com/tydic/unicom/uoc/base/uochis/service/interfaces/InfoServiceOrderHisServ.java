package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderHisPo;

public interface InfoServiceOrderHisServ {
	/**
	 * 写服务订单表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */

	public boolean createInfoServiceOrderHisPo(InfoServiceOrderHisPo po)throws Exception;

	public List<InfoServiceOrderHisPo> queryInfoServiceOrderHisByOrderNo(InfoServiceOrderHisPo po)throws Exception;

	public List<InfoServiceOrderHisPo> queryInfoServiceOrderHisByPo(InfoServiceOrderHisPo po)throws Exception;
}

package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderPayHisPo;

public interface InfoServiceOrderPayHisServ {
	/**
	 * 写服务订单收费详情表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoServiceOrderPayHisPo(InfoServiceOrderPayHisPo po)throws Exception;

	public List<InfoServiceOrderPayHisPo> queryInfoServiceOrderPayHisByOrderNo(InfoServiceOrderPayHisPo po)throws Exception;
}

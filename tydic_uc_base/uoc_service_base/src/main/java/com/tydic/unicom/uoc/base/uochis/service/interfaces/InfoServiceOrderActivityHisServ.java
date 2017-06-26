package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderActivityHisPo;

public interface InfoServiceOrderActivityHisServ {
	/**
	 * 写服务订单优惠活动历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoServiceOrderActivityHisPo(InfoServiceOrderActivityHisPo po)throws Exception;

	public List<InfoServiceOrderActivityHisPo> queryInfoServiceOrderActivityHisByOrderNo(InfoServiceOrderActivityHisPo po)throws Exception;
}

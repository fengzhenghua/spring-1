package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderDeveloperHisPo;

public interface InfoServiceOrderDeveloperHisServ {
	
	/**
	 * 写服务订单发展人表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoServiceOrderDeveloperHisPo(InfoServiceOrderDeveloperHisPo po)throws Exception;

	public List<InfoServiceOrderDeveloperHisPo> queyInfoServiceOrderDeveloperHisByOrderNo(InfoServiceOrderDeveloperHisPo po)throws Exception;
}

package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderM165HisPo;

public interface InfoServiceOrderM165HisServ {
	
	/**
	 * 写服务订单宽带信息表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean createInfoServiceOrderM165HisPo(InfoServiceOrderM165HisPo po)throws Exception;
	
	public List<InfoServiceOrderM165HisPo> queryInfoServiceOrderM165HisByOrderNo(InfoServiceOrderM165HisPo po)throws Exception;
	

}

package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderFixHisPo;

public interface InfoServiceOrderFixHisServ {
	/**
	 * 写服务订单固网信息表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoServiceOrderFixHisPo(InfoServiceOrderFixHisPo po)throws Exception;

	public List<InfoServiceOrderFixHisPo> queryInfoServiceOrderFixHisByOrderNo(InfoServiceOrderFixHisPo po)throws Exception;
}

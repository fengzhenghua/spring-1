package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderTerminalHisPo;

public interface InfoServiceOrderTerminalHisServ {
	/**
	 * 写服务订单终端表历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	
	public boolean createInfoServiceOrderTerminalHisPo(InfoServiceOrderTerminalHisPo po)throws Exception;

	public List<InfoServiceOrderTerminalHisPo> queryInfoServiceOrderTerminalHisByOrderNo(InfoServiceOrderTerminalHisPo po)throws Exception;
}

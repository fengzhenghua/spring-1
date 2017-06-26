package com.tydic.unicom.uoc.service.order.his.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderTerminalHisVo;

public interface InfoServiceOrderTerminalHisServDu {

	
	public List<InfoServiceOrderTerminalHisVo> queryInfoServiceOrderTerminalHisByOrderNo(InfoServiceOrderTerminalHisVo vo)throws Exception;
}

package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderTerminalVo;

public interface InfoServiceOrderTerminalServDu {

	//public List<InfoServiceOrderTerminalVo> queryInfoServiceOrderTerminalByOfrOrderNo(InfoServiceOrderTerminalVo vo) throws Exception;

	public List<InfoServiceOrderTerminalVo> queryInfoServiceOrderTerminalByOrderNo(InfoServiceOrderTerminalVo vo)throws Exception;
}

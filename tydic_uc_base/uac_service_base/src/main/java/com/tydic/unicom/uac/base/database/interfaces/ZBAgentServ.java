package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.ZBInfoAgentPo;

public interface ZBAgentServ {

	public ZBInfoAgentPo queryZBInfoAgentByChnlCode(ZBInfoAgentPo zbInfoAgent);
	
	/**
	 * 获取可选渠道，默认取前1000条
	 * 根据渠道编码、渠道名称、地区编码
	 * @param zbInfoAgent
	 * @return
	 */
	public List<ZBInfoAgentPo> queryZBInfoAgentByChnlCodeOrNameOrRegionId(ZBInfoAgentPo zbInfoAgent);
}

package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.InfoAgentDevelopersPo;

public interface InfoAgentDevelopersServ {

	public InfoAgentDevelopersPo queryInfoAgentDevelopersByDevCode(InfoAgentDevelopersPo infoAgentDevelopersPo);

	public InfoAgentDevelopersPo queryInfoAgentDevelopersByOperNo(InfoAgentDevelopersPo infoAgentDevelopersPo);

	public List<InfoAgentDevelopersPo> queryInfoAgentDevelopers(InfoAgentDevelopersPo infoAgentDevelopersPo);
	
	/**
	 * 获取可选发发展人
	 * @param infoAgentDevelopersPo 发展人编码、发展人姓名、渠道编码、地区编码
	 * @return
	 */
	public List<InfoAgentDevelopersPo> queryInfoAgentDevelopersByCodeOrName(InfoAgentDevelopersPo infoAgentDevelopersPo);
}

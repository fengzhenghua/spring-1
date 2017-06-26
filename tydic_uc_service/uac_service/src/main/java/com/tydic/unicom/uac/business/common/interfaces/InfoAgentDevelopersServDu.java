package com.tydic.unicom.uac.business.common.interfaces;

import java.util.List;

import com.tydic.unicom.uac.business.common.vo.InfoAgentDevelopersVo;

public interface InfoAgentDevelopersServDu {

	public InfoAgentDevelopersVo queryInfoAgentDevelopersByDevCode(InfoAgentDevelopersVo infoAgentDevelopersVo)throws Exception;

	public InfoAgentDevelopersVo queryInfoAgentDevelopersByOperNo(InfoAgentDevelopersVo infoAgentDevelopersVo)throws Exception;
	
	/**
	 * UAC0004-获取可选发展人
	 * @param infoAgentDevelopersVo
	 * @return
	 * @throws Exception
	 */
	public List<InfoAgentDevelopersVo> queryInfoAgentDevelopers(InfoAgentDevelopersVo infoAgentDevelopersVo)throws Exception;

}

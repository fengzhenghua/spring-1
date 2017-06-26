package com.tydic.unicom.uac.business.common.interfaces;

import java.util.List;

import com.tydic.unicom.uac.business.common.vo.ZBInfoAgentCodeAndNameVo;
import com.tydic.unicom.uac.business.common.vo.ZBInfoAgentVo;

/**
 * 服务定义
 * UAC0008-获取可选渠道
 * @author ZhangCheng
 * @date 2017-04-13
 *
 */
public interface InfoAgentServDu {
	
	/**
	 * UAC0008-获取可选渠道
	 * @param zBInfoAgentVo
	 * @return
	 */
	List<ZBInfoAgentCodeAndNameVo> queryZbInfoAgents(ZBInfoAgentVo zBInfoAgentVo);
	
}

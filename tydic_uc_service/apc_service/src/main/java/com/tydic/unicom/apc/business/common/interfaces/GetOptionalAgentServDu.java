package com.tydic.unicom.apc.business.common.interfaces;


import com.tydic.unicom.apc.business.common.vo.InfoAgentVo;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 触点中心服务
 * APC0012-获取可选渠道<br>
 * @author ZhangCheng
 * @date 2017-04-14
 */
public interface GetOptionalAgentServDu {
	
	/**
	 * APC0012-获取可选渠道
	 * @param infoAgentVo
	 * @return
	 * @throws Exception
	 */
	UocMessage GetOptionalAgentInfo(InfoAgentVo infoAgentVo) throws Exception;

}

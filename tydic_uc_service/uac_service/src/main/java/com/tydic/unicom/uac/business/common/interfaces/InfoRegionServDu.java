package com.tydic.unicom.uac.business.common.interfaces;

import java.util.List;

import com.tydic.unicom.uac.business.common.vo.InfoRegionVo;

/**
 * 服务定义
 * UAC0009-获取可选地区
 * @author ZhangCheng
 * @date 2017-04-12
 * @version V1.0
 */
public interface InfoRegionServDu {
	
	/**
	 * UAC0009-获取可选地区
	 * @param infoRegionVo
	 * @return
	 */
	List<InfoRegionVo> queryInfoRegions(InfoRegionVo infoRegionVo) throws Exception;
	
	
}

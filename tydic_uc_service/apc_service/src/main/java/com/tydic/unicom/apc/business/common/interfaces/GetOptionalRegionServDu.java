package com.tydic.unicom.apc.business.common.interfaces;

import com.tydic.unicom.apc.business.common.vo.InfoRegionVo;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 触点中心服务
 * APC0013-获取可选地区<br>
 * @author ZhangCheng
 * @date 2017-04-14
 */
public interface GetOptionalRegionServDu {
	
	UocMessage  GetOptionalRegionInfo(InfoRegionVo infoRegionVo) throws Exception;

}

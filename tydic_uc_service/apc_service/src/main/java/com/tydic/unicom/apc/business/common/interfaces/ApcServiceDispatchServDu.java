package com.tydic.unicom.apc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月6日 下午6:21:43
 * @ClassName ApcServiceDispatchServDu
 * @Description apc服务调度
 * @version V1.0
 */
public interface ApcServiceDispatchServDu {
	
	UocMessage createHandler(String json_info);
}

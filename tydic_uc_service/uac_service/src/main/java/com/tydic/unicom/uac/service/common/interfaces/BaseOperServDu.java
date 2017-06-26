package com.tydic.unicom.uac.service.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>
 * 认证中心服务
 * <p>
 * UACBS0001-获取登录信息<br>
 * 
 * @author ZhangCheng
 * @version V1.0
 * @date 2017-02-28
 */
public interface BaseOperServDu {

	/**
	 * UACBS0001-获取登录信息
	 * 
	 * @param jsessionId
	 *            工号或登录验证串
	 * @return 对应的操作员信息
	 */
	UocMessage getOperInfoByJsessionId(String jsessionId) throws Exception;

}

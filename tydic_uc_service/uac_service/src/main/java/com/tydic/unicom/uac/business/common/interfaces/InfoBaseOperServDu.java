package com.tydic.unicom.uac.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>
 * 认证中心服务
 * <p>
 * UAC0005-获取登录信息<br>
 * 
 * @author ZhangCheng
 * @version V1.0
 * @date 2017-03-02
 */
public interface InfoBaseOperServDu {

	/**
	 * UAC0005-获取登录信息 <br>
	 * 调UACBS0001接口
	 * 
	 * @param jsessionId
	 *            工号或登录验证串
	 * @return 对应的操作员信息
	 */
	UocMessage getOperInfoByJsessionId(String jsessionId) throws Exception;

}

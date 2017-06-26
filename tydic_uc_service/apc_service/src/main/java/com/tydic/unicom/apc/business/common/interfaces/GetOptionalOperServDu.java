package com.tydic.unicom.apc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>
 * 触点中心服务
 * </p>
 * APC0002-获取可选工号<br>
 * 
 * @author ZhangCheng
 * @version V1.0
 * @date 2017-03-06
 */
public interface GetOptionalOperServDu {

	/**
	 * <p>
	 * APC0002-获取可选工号
	 * </p>
	 * 
	 * 通过APCBS0001能力平台服务调用UAC认证中心服务UAC0003
	 * 
	 * @param jsessionId
	 *            验证字符串
	 * @param operNo
	 *            工号编码
	 * @param operName
	 *            工号名称
	 * @return
	 * @throws Exception
	 */
	UocMessage getOptionalOperInfoString(String jsessionId, String operNo, String operName) throws Exception;

}

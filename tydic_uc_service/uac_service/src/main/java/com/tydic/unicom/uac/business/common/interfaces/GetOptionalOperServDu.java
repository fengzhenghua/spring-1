package com.tydic.unicom.uac.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>
 * 认证中心服务
 * <p>
 * UAC0003-获取可选工号<br>
 *
 * @author ZhangCheng
 * @version V1.0
 * @date 2017-03-02
 */
public interface GetOptionalOperServDu {

	/**
	 * UAC0003-获取可选工号
	 * @param jsessionId  验证字符串
	 * @param operNo  工号编码
	 * @param operName  工号名称
	 * @return
	 * @throws Exception
	 */
	public UocMessage getOptionalOperInfoString(String jsessionId, String operNo, String operName) throws Exception;

	/**
	 * UAC0010-获取可选即时激励工号
	 * @param jsession_id
	 * @param developer_no
	 * @return
	 * @throws Exception
	 */
	public UocMessage getOptionalRewardOperInfo(String jsession_id, String developer_no) throws Exception;
}

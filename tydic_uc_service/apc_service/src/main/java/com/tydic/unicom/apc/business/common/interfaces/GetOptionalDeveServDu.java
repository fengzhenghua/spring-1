package com.tydic.unicom.apc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>
 * 触点中心服务
 * </p>
 * APC0003-获取可选发展人<br>
 *
 * @author ZhangCheng
 * @version V1.0
 * @date 2017-03-08
 */
public interface GetOptionalDeveServDu {

	/**
	 * APC0003-获取可选发展人
	 * 通过APCBS0001能力平台服务调用UAC认证中心服务UAC0004
	 * @param jsession_id 验证字符串
	 * @param developer_no 发展人编码
	 * @param developer_name 发展人名称
	 * @param region 发展人所属区域
	 * @param chnl_code 发展人所属渠道
	 * @return
	 * @throws Exception
	 */
	public UocMessage getOptionalDeveInfo(String jsession_id, String developer_no, String developer_name, String region, String chnl_code)
			throws Exception;

	/**
	 * APC0017-获取可选即时激励发展人
	 * @param jsession_id
	 * @param developer_no
	 * @return
	 * @throws Exception
	 */
	public UocMessage getRewardDevelopInfo(String jsession_id, String developer_no) throws Exception;
}

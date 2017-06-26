package com.tydic.unicom.apc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

/**
 * <p>
 * 触点中心服务
 * </p>
 * APC0004-获取可选商品<br>
 * 
 * @author ZhangCheng
 * @version V1.0
 * @date 2017-03-09
 */
public interface GetOptionalGoodsServDu {

	/**
	 * <p>
	 * APC0004-获取可选商品
	 * </p>
	 * 通过APCBS0001能力平台服务调用商品中心服务UGC0002
	 * 
	 * @param jsession_id
	 *            验证字符串
	 * @return
	 */
	UocMessage getOptionalGoodsInfo(String jsession_id) throws Exception;

}

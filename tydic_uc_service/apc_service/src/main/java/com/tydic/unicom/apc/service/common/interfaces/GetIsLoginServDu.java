package com.tydic.unicom.apc.service.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

/**
 * 触点中心服务-校验是否登录<br>
 * 校验是否登录，通过jsession_id<br>
 * 通过能力平台调认证中心服务
 * @author ZhangCheng
 * @date 2017-03-10
 * @version V1.0
 */
public interface GetIsLoginServDu {
	
	/**
	 * 校验是否登录
	 * @param jsession_id 验证字符串
	 * @return 校验结果
	 */
	UocMessage GetIsLoginByJsessionId(String jsession_id) throws Exception;
}

package com.tydic.unicom.apc.service.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface ApcAbilityPlatformServDu {
	/**
	 * 触点调用能力平台公共方法APCBS0001
	 * @param json_info
	 * @param interface_code
	 * @param center_code
	 * @return
	 * @throws Exception
	 */

	public UocMessage CallApcAbilityPlatform(String json_info,String interface_code,String center_code)throws Exception;

}

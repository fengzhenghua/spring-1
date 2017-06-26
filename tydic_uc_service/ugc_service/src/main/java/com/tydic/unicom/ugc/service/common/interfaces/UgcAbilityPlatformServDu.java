package com.tydic.unicom.ugc.service.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface UgcAbilityPlatformServDu {
	
	/**
	 * 商品中心调用能力平台公共方法UGCBS0001
	 * @param json_info
	 * @param interface_code
	 * @param center_code
	 * @return
	 * @throws Exception
	 */

	public UocMessage CallUgcAbilityPlatform(String json_info,String interface_code,String center_code)throws Exception;

}

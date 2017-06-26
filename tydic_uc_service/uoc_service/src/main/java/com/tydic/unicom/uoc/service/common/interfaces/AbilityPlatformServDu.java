package com.tydic.unicom.uoc.service.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface AbilityPlatformServDu {
	/**
	 * 调用能力平台  BASE0018
	 * @param jsession_id
	 * @param json_info
	 * @param interface_type
	 * @param interface_param_json
	 * @return
	 * @throws Exception
	 */
	
	public UocMessage CallAbilityPlatform(String json_info,String interface_type,String interface_param_json,String interface_url)throws Exception;

}

package com.tydic.unicom.uif.service.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface AbilityPlatformForUocServDu {
	
	/**
	 * 调用能力平台  BASE0018
	 * @param jsession_id
	 * @param json_info
	 * @param interface_type
	 * @param interface_param_json
	 * @return
	 * @throws Exception
	 */
	
	public UocMessage CallAbilityPlatformForUoc(String json_info,String interface_type,String interface_param_json,String interface_url)throws Exception;


}

package com.tydic.unicom.uif.business.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface InterfaceCenterAbilityServDu {
	
	/**
	 * 接口中心能力平台调用分发
	 * @param json_info
	 * @param interface_code
	 * @param center_code
	 * @return
	 * @throws Exception
	 */
	
	public UocMessage CreateAbilityPlatform(String json_info)throws Exception;

}

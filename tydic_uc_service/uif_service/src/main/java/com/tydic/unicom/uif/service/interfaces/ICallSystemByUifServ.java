package com.tydic.unicom.uif.service.interfaces;

import java.util.List;

import com.tydic.unicom.webUtil.UocMessage;


public interface ICallSystemByUifServ {
	
	/**
	 * 
	 * @author heguoyong 2017年5月10日 下午5:37:19
	 * @Method: callAbilityPlatform 
	 * @Description: 调用其它中心
	 * @param json
	 */
	UocMessage callOtherSystem(String paramsValueJson,List<String> params);
}

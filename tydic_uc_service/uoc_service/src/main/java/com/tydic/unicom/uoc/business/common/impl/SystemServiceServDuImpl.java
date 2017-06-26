package com.tydic.unicom.uoc.business.common.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.business.common.interfaces.SystemServiceServDu;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.webUtil.UocMessage;

public class SystemServiceServDuImpl implements SystemServiceServDu{

	private static Logger logger = Logger.getLogger(SystemServiceServDuImpl.class);
	
	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;
	
	@Override
	public UocMessage loginOutMethod(String jsessionId) throws Exception {
		logger.info("==================退出===================");
		UocMessage uocMessage = new UocMessage();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "logout");
		Map<String,String> map = new HashMap<String,String>();
		map.put("jession_id", jsessionId);
		jsonObj.put("param", map);
		//调用能力平台接口
		uocMessage = abilityPlatformServDu.CallAbilityPlatform(jsonObj.toString(), "300", "", "");
		return uocMessage;
	}

}

package com.tydic.unicom.apc.business.common.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.apc.business.common.interfaces.GetInfoServDu;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.webUtil.UocMessage;

public class GetInfoServDuImpl implements GetInfoServDu{

	private static Logger logger = Logger.getLogger(GetInfoServDuImpl.class);
	
	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;
	
	@Override
	public UocMessage getBusinessHallInfo(String jsessionId, String areaId,
			String departNo, String departName) throws Exception {
		
		UocMessage uocMessage = new UocMessage();
		
		//拼装报文
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "getBusinessHallInfo");
		Map<String,String> map = new HashMap<String,String>();
		map.put("jsessionId", jsessionId);
		map.put("areaId", areaId);
		map.put("departNo", departNo);
		map.put("departName", departName);
		jsonObj.put("param", map);
		logger.debug("=================通过能力平台去uac获取可选营业厅信息(报文):"+jsonObj.toString());
		uocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(jsonObj.toString(), "", "UAC");
		return uocMessage;
	}

	@Override
	public UocMessage getOperInfo(String jsessionId) throws Exception {
		UocMessage uocMessage = new UocMessage();
		
		//拼装报文
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "getBaseOperInfo");
		Map<String,String> map = new HashMap<String,String>();
		map.put("jsession_id", jsessionId);
		jsonObj.put("param", map);
		logger.debug("=================通过能力平台去uac获取工号信息(报文):"+jsonObj.toString());
		uocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(jsonObj.toString(), "", "UAC");
		return uocMessage;
	}

}

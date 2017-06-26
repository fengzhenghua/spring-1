package com.tydic.unicom.ugc.business.common.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.ugc.business.common.interfaces.AbilityPlatformForUgcServDu;
import com.tydic.unicom.ugc.service.common.interfaces.FunctionReflecServDu;
import com.tydic.unicom.ugc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class AbilityPlatformForUgcServDuImpl implements AbilityPlatformForUgcServDu{
	
	Logger logger = Logger.getLogger(AbilityPlatformForUgcServDuImpl.class);

	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	@Autowired
	private FunctionReflecServDu functionReflecServDu;

	@Override
	public UocMessage createPlatformForUgc(String json_info) throws Exception{
		UocMessage message= new UocMessage();
		logger.info("------json_info------"+json_info);
		try {
			JSONObject json = JSONObject.fromObject(json_info); 
			String serviceName=(String) json.get("SERVICE_NAME");
			logger.info("------SERVICE_NAME------"+serviceName);
			String json_in=json.get("params").toString();
			logger.info("------params------"+json_in);

			Map<String,String> serviceMap = getValueFromProperties(serviceName);
			String className = serviceMap.get(serviceName+"Class");
			String methodName = serviceMap.get(serviceName+"Method");
			Object[] paramValues = new String[]{json_in};
			Class[] paramTypes = new Class[]{String.class};

			message=(UocMessage) functionReflecServDu.invokeMethod(className, methodName, paramValues, paramTypes);
		} catch (Throwable e) {
			logger.info("具体异常信息----------"+e);
			logger.error("===============abilityPlatformForUgc异常==============");
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("系统异常");
			return message;
		}	

		return message;
	}



	public Map<String,String> getValueFromProperties(String key) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		Properties props = new Properties();
		InputStream in = AbilityPlatformForUgcServDuImpl.class.getResourceAsStream("/UgcServiceCode.properties");
		props.load(in);
		map.put(key+"Class", props.getProperty(key+"Class"));
		map.put(key+"Method", props.getProperty(key+"Method"));
		return map;
	}

}

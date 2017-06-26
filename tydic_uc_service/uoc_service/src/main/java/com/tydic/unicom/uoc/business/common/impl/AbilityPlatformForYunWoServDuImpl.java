package com.tydic.unicom.uoc.business.common.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.business.common.interfaces.AbilityPlatformForYunWoServDu;
import com.tydic.unicom.uoc.service.common.interfaces.FunctionReflecServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.webUtil.UocMessage;

public class AbilityPlatformForYunWoServDuImpl implements AbilityPlatformForYunWoServDu{

	Logger logger = Logger.getLogger(AbilityPlatformForYunWoServDuImpl.class);

	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	@Autowired
	private FunctionReflecServDu functionReflecServDu;

	@Override
	public UocMessage createPlatformForYunWo(String json_info) throws Throwable {
		UocMessage message= new UocMessage();
		logger.info("------json_info------"+json_info);
		// Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(json_info);
		// map=jsonToBeanServDu.jsonToMap(json_info);
		String serviceName = (String) json.get("SERVICE_NAME");
		logger.info("------SERVICE_NAME------" + serviceName);
		String json_in = json.get("params").toString();
		logger.info("------params------" + json_in);

		Map<String, String> serviceMap = getValueFromProperties(serviceName);
		String className = serviceMap.get(serviceName + "Class");
		String methodName = serviceMap.get(serviceName + "Method");
		Object[] paramValues = new String[] { json_in };
		Class[] paramTypes = new Class[] { String.class };

		logger.info("===============abilityPlatformForYunWo  Class==============" + className);
		logger.info("===============abilityPlatformForYunWo  methodName==============" + methodName);
		message = (UocMessage) functionReflecServDu.invokeMethod(className, methodName, paramValues, paramTypes);
		// } catch (Throwable e) {
		// logger.error("===============abilityPlatformForYunWo异常=============="
		// + e.getMessage());
		// e.printStackTrace();
		// message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
		// message.setContent("系统异常");
		// return message;
		// }

		return message;
	}



	public Map<String,String> getValueFromProperties(String key) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		Properties props = new Properties();
		InputStream in = ActivemqOrderFinishListenerServDuImpl.class.getResourceAsStream("/serviceCode.properties");
		props.load(in);
		map.put(key+"Class", props.getProperty(key+"Class"));
		map.put(key+"Method", props.getProperty(key+"Method"));
		return map;
	}

}

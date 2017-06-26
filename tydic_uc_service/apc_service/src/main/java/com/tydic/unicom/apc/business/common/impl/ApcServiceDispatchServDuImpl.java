package com.tydic.unicom.apc.business.common.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.tydic.unicom.apc.business.common.interfaces.ApcServiceDispatchServDu;
import com.tydic.unicom.apc.business.common.interfaces.IApcServiceHandler;
import com.tydic.unicom.exception.BusinessException;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

import net.sf.json.JSONObject;

public class ApcServiceDispatchServDuImpl implements ApcServiceDispatchServDu{

	@Autowired
	private ApplicationContext context;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public UocMessage createHandler(String json_info) {
		UocMessage message= new UocMessage();
		try {
			JSONObject json = JSONObject.fromObject(json_info); 
			String serviceName=(String) json.get("SERVICE_NAME");
			logger.info("------SERVICE_NAME------"+serviceName);
			String json_in=json.get("params").toString();
			logger.info("------params------"+json_in);
			//获取bean名称，和需要调用的method
			
			Map<String,String> serviceMap = getValueFromProperties(serviceName);
			String handlerName = serviceMap.get(serviceName+"Class");
			String  methodName = serviceMap.get(serviceName+"Method");
			IApcServiceHandler handler = null;
			
			handler = (IApcServiceHandler)context.getBean(handlerName);
			if(handler!=null){
				message = handler.getMessage(json_in,methodName);
			}else{
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent(handlerName+"不存在");
				return message;
			}		
		} catch (BusinessException e) {
			logger.error("===============ApcServiceDispatchServDu异常=============="+e.getMessage(),e);
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("apc系统异常");
			return message;
		}	
        return message;
	}
	
	
	
	public Map<String,String> getValueFromProperties(String key) throws BusinessException{
		Map<String,String> map = new HashMap<String,String>();
		Properties props = new Properties();
		InputStream in = ApcServiceDispatchServDuImpl.class.getResourceAsStream("/ApcServiceCode.properties");
		try {
			props.load(in);
		} catch (Exception e) {
			
			logger.error("===============ApcServiceDispatchServDu异常=============="+e.getMessage(),e);
			throw (BusinessException) e;
		}
		map.put(key+"Class", props.getProperty(key+"Class"));
		map.put(key+"Method", props.getProperty(key+"Method"));
		return map;
	}
}

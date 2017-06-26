package com.tydic.unicom.uif.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.tydic.unicom.aspect.annotation.SystemAuditLog;
import com.tydic.unicom.uif.service.interfaces.ICallSystemByUifServ;
import com.tydic.unicom.util.HttpUtil;
import com.tydic.unicom.webUtil.BusiMessage;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Component("callUocCenterHandler")
public class CallUocCenterHandler implements  ICallSystemByUifServ{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${uoc.service.url}")
	private String url;


	@Override
	@SystemAuditLog
	public UocMessage callOtherSystem(String paramsValueJson,List<String> params) {
		UocMessage message= new UocMessage();
		logger.info("进入调用UOC服务,入参" + paramsValueJson);
		String dataStr = "json_info="+String.valueOf(paramsValueJson);
		BusiMessage<String> response=HttpUtil.formHttpPost(url, dataStr);
		
		if(response.getSuccess()){
			message = JSON.parseObject(String.valueOf(response.getData()),UocMessage.class);
		}else{
			message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			message.setContent("调用uoc服务失败");
			message.addArg("code","9999");
		}
		
		return message;
	}
	
}

package com.tydic.unicom.uoc.service.activiti.impl;

import java.io.InputStream;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessDefinitionServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ProcessDefinitionServDu")
public class ProcessDefinitionServDuImpl implements ProcessDefinitionServDu{

	private static Logger logger = Logger.getLogger(ProcessDefinitionServDuImpl.class);
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Override
	public UocMessage findProcessDefinition() throws Exception {
		logger.info("<<<<<<==============调用获取流程定义信息服务=============>>>>>>");
		UocMessage uocMessage = new UocMessage();
		
		String url = propertiesParamVo.getFindProcessDefinition();
		String resultJsonStr = HttpUtil.httpClient(url,"");
		logger.info("============================调用工作流获取流程定义信息，返回结果："+resultJsonStr);
		JSONObject resultJsonObj = ActivitiUtils.StringToJson(resultJsonStr);
		if("success".equals(resultJsonObj.getString("errorCode"))){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.addArg("processDefinitionInfo", resultJsonObj.get("processDefinitionInfo"));
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent(resultJsonObj.getString("content"));
		}
		return uocMessage;
	}

}

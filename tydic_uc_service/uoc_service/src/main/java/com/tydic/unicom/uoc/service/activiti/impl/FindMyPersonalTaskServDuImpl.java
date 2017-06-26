package com.tydic.unicom.uoc.service.activiti.impl;


import java.io.InputStream;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.service.activiti.interfaces.FindMyPersonalTaskServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("FindMyPersonalTaskServDu")
public class FindMyPersonalTaskServDuImpl implements FindMyPersonalTaskServDu{

	private static Logger logger = Logger.getLogger(FindMyPersonalTaskServDuImpl.class);

	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Override
	public UocMessage findMyPersonalTaskByInstanceId(String proc_inst_id,String order_no) throws Exception {
		logger.info("<<<<<<===============获取当前任务服务==============>>>>>>>>");
		UocMessage uocMessage = new UocMessage(); 
		int modId = Math.abs(order_no.hashCode()%2);
		String url = getValueFromProperties(modId);
		JSONObject requestJsonObj = new JSONObject();
		requestJsonObj.put("processInstanceId", proc_inst_id);
		//调用工作流查询当前任务
		String resultJsonStr = HttpUtil.httpClient(url,requestJsonObj.toString());
		logger.info("============================调用工作流查询当前任务，返回结果："+resultJsonStr);
		JSONObject resultJsonObj = ActivitiUtils.StringToJson(resultJsonStr);
		if("success".equals(resultJsonObj.getString("errorCode"))){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.addArg("current_task", resultJsonObj.getString("current_task"));
			uocMessage.addArg("current_tache", resultJsonObj.getString("current_tache"));
			uocMessage.addArg("current_task_name", resultJsonObj.getString("current_task_name"));
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent(resultJsonObj.getString("content"));
			return uocMessage;
		}
	}
	
	
	/**
	 * 获取配置文件工作流访问路径参数的值
	 * */
	public String getValueFromProperties(int modId) throws Exception{
		String value = "";
		
		if(modId == 0){
			value = propertiesParamVo.getFindPersonalTask_0();
		}
		if(modId == 1){
			value = propertiesParamVo.getFindPersonalTask_1();
		}
		return value;
	}

}

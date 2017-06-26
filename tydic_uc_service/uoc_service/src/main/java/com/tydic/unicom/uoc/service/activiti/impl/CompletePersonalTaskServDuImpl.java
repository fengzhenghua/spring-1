package com.tydic.unicom.uoc.service.activiti.impl;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.service.activiti.interfaces.CompletePersonalTaskServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("CompletePersonalTaskServDu")
public class CompletePersonalTaskServDuImpl implements CompletePersonalTaskServDu{

	Logger logger = Logger.getLogger(CompletePersonalTaskServDuImpl.class);
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Override
	public UocMessage completeProcess(String proc_inst_id, String order_no) throws Exception {
		UocMessage uocMessage = new UocMessage();
		int modId = Math.abs(order_no.hashCode()%2);
		String url = getValueFromPropertiesForCompleteProcess(modId);
		JSONObject requestJsonObj = new JSONObject();
		requestJsonObj.put("processInstanceId", proc_inst_id);
		//调用工作流提交当前流程
		String resultJsonStr = HttpUtil.httpClient(url,requestJsonObj.toString());		
		logger.info("==================================调用工作流提交当前流程，返回结果："+resultJsonStr);
		JSONObject resultJsonObj = ActivitiUtils.StringToJson(resultJsonStr);
		if("success".equals(resultJsonObj.getString("errorCode"))){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("提交流程成功");
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent(resultJsonObj.getString("content"));
			return uocMessage;
		}
	}
	
	@Override
	public UocMessage completePersonalTask(String proc_inst_id, String order_no,Map<String,String> actionCode) throws Exception {
		UocMessage uocMessage = new UocMessage();
		int modId = Math.abs(order_no.hashCode()%2);
		String url = getValueFromProperties(modId);
		JSONObject requestJsonObj = new JSONObject();
		if(actionCode == null){
			requestJsonObj.put("condParam1", "auto");
		}
		else{
			requestJsonObj.put("condParam1", actionCode.get("condParam1"));
		}
		requestJsonObj.put("processInstanceId", proc_inst_id);
		//调用工作流提交当前任务
		String resultJsonStr = HttpUtil.httpClient(url,requestJsonObj.toString());
		logger.info("============================调用工作流提交当前任务，返回结果："+resultJsonStr);
		JSONObject resultJsonObj = ActivitiUtils.StringToJson(resultJsonStr);
		if("success".equals(resultJsonObj.getString("errorCode"))){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("提交任务成功");
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent(resultJsonObj.getString("content"));
			return uocMessage;
		}
	}

	/**
	 * 获取配置文件参数的值
	 * */
	public String getValueFromProperties(int modId) throws Exception{
		String value = "";
		
		if(modId == 0){
			value = propertiesParamVo.getCompletePersonalTask_0();
		}
		if(modId == 1){
			value = propertiesParamVo.getCompletePersonalTask_1();
		}
		return value;
	}
	
	public String getValueFromPropertiesForCompleteProcess(int modId) throws Exception{
		String value = "";
		
		if(modId == 0){
			value = propertiesParamVo.getCompleteProcess_0();
		}
		if(modId == 1){
			value = propertiesParamVo.getCompleteProcess_1();
		}
		return value;
	}

}

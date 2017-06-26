package com.tydic.unicom.uoc.service.activiti.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessHistoryServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ProcessHistoryServDu")
public class ProcessHistoryServDuImpl implements ProcessHistoryServDu{

	private static Logger logger = Logger.getLogger(ProcessHistoryServDuImpl.class);
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Override
	public UocMessage findProcessHistoryInfo(String processInstanceId, String orderNo) throws Exception {
		logger.info("<<<<<<==============获取流程实例历史信息服务==============>>>>>>");
		UocMessage uocMessage = new UocMessage();
		int modId = Math.abs(orderNo.hashCode()%2);
		String url = getValueFromProperties(modId);
		JSONObject requestJsonObj = new JSONObject();
		requestJsonObj.put("processInstanceId", processInstanceId);
		//调用工作流获取流程实例历史信息
		String resultJsonStr = HttpUtil.httpClient(url,requestJsonObj.toString());
		logger.info("============================调用工作流获取流程实例历史信息，返回结果："+resultJsonStr);
		JSONObject resultJsonObj = ActivitiUtils.StringToJson(resultJsonStr);
		if("success".equals(resultJsonObj.getString("errorCode"))){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			JSONObject instanceJsonObj = (JSONObject) resultJsonObj.get("InstanceInfo");
			JSONArray taskJsonArr = (JSONArray) resultJsonObj.get("TaskInfo");
			Map<String,String> instanceMap = new HashMap<String,String>();
			instanceMap.put("InstanceId", instanceJsonObj.getString("InstanceId"));
			instanceMap.put("InstanceName", instanceJsonObj.getString("InstanceName"));
			instanceMap.put("InstanceStartTime", instanceJsonObj.getString("InstanceStartTime"));
			instanceMap.put("InstanceEndTime", instanceJsonObj.getString("InstanceEndTime"));
			List<Map<String,String>> taskMapList = new ArrayList<Map<String,String>>();
			Iterator iterator = taskJsonArr.iterator();
			while(iterator.hasNext()){
				JSONObject taskJsonObj = (JSONObject) iterator.next();
				Map<String,String> taskMap = new HashMap<String,String>();
				taskMap.put("TaskId", taskJsonObj.getString("TaskId"));
				taskMap.put("TaskName", taskJsonObj.getString("TaskName"));
				taskMap.put("TaskAssignee", taskJsonObj.getString("TaskAssignee"));
				taskMap.put("TaskStartTime", taskJsonObj.getString("TaskStartTime"));
				taskMap.put("TaskEndTime", taskJsonObj.getString("TaskEndTime"));
				taskMapList.add(taskMap);
			}
			uocMessage.addArg("InstanceInfo", instanceMap);
			uocMessage.addArg("TaskInfo", taskMapList);
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent(resultJsonObj.getString("content"));
		}
		return uocMessage;
	}

	/**
	 * 获取配置文件工作流访问路径参数的值
	 * */
	public String getValueFromProperties(int modId) throws Exception{
		String value = "";
		
		if(modId == 0){
			value = propertiesParamVo.getFindProcessHistoryInfo_0();
		}
		if(modId == 1){
			value = propertiesParamVo.getFindProcessHistoryInfo_1();
		}
		return value;
	}
}

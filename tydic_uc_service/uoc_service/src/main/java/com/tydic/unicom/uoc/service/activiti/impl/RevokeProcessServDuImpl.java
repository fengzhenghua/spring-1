package com.tydic.unicom.uoc.service.activiti.impl;

import java.io.InputStream;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.service.activiti.interfaces.RevokeProcessServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("RevokeProcessServDu")
public class RevokeProcessServDuImpl implements RevokeProcessServDu{

	Logger logger = Logger.getLogger(RevokeProcessServDuImpl.class);
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Override
	public UocMessage revokeProcess(String proc_inst_id, String order_no) throws Exception {
		UocMessage uocMessage = new UocMessage();
		int modId = Math.abs(order_no.hashCode()%2);
		String url = getValueFromProperties(modId);
		JSONObject requestJsonObj = new JSONObject();
		requestJsonObj.put("processInstanceId", proc_inst_id);
		//调用工作流撤销任务
		String resultJsonStr = HttpUtil.httpClient(url,requestJsonObj.toString());
		logger.info("============================调用工作流撤销任务，返回结果："+resultJsonStr);
		JSONObject resultJsonObj = ActivitiUtils.StringToJson(resultJsonStr);
		if("success".equals(resultJsonObj.getString("errorCode"))){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("撤销任务成功");
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent(resultJsonObj.getString("content"));
		}
		return uocMessage;
	}

	/**
	 * 获取配置文件参数的值
	 * */
	public String getValueFromProperties(int modId) throws Exception{
		String value = "";
		
		if(modId == 0){
			value = propertiesParamVo.getRevokeProcess_0();
		}
		if(modId == 1){
			value = propertiesParamVo.getRevokeProcess_1();
		}
		return value;
	}
}

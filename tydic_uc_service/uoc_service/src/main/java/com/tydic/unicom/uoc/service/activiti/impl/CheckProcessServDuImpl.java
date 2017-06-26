package com.tydic.unicom.uoc.service.activiti.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.service.activiti.interfaces.CheckProcessServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcFlowParamServDu;
import com.tydic.unicom.uoc.service.activiti.vo.ProcFlowParamVo;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("CheckProcessServDu")
public class CheckProcessServDuImpl implements CheckProcessServDu{

	Logger logger = Logger.getLogger(CheckProcessServDuImpl.class);
	
	@Autowired
	private ProcFlowParamServDu procFlowParamServDu;
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Override
	public UocMessage checkProcess(String proc_inst_id, String order_no,String flow_type, Map<String, String> action_code) throws Exception {
		boolean isOk = false;
		UocMessage uocMessage = new UocMessage();
		int modId = Math.abs(order_no.hashCode()%2);
		logger.info("=====================<<<<<<<<<<<<modId："+modId);
		String url = getValueFromProperties(modId);
		logger.info("=====================<<<<<<<<<<<<url："+url);
		JSONObject requestJsonObj = new JSONObject();
		requestJsonObj.put("processInstanceId", proc_inst_id);
		//调用工作流校验当前流程是否已结束，如为结束，返回流程定义id与当前任务定义Id
		String resultJsonStr = HttpUtil.httpClient(url,requestJsonObj.toString());
		logger.info("============================调用工作流校验当前流程，返回结果："+resultJsonStr);
		JSONObject resultJsonObj = ActivitiUtils.StringToJson(resultJsonStr);
		if(!"success".equals(resultJsonObj.getString("errorCode"))){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent(resultJsonObj.getString("content"));
			return uocMessage;
		}
		else{
			ProcFlowParamVo procFlowParamVo = new ProcFlowParamVo();
			procFlowParamVo.setProc_mod_code(resultJsonObj.getString("processDefinitionId"));
			procFlowParamVo.setTache_code(resultJsonObj.getString("tacheCode"));
			List<ProcFlowParamVo> list = procFlowParamServDu.queryProcFlowParamByProcModCodeAndTacheCode(procFlowParamVo);
			if(list != null && list.size()>0){
				if(action_code == null){
					isOk = true;
				}
				else{
					Set<String> keySet = action_code.keySet();
					String condParam = "";
					for(String key:keySet){
						condParam = condParam+key+"="+action_code.get(key)+",";
					}
					for(int i=0;i<list.size();i++){
						if(list.get(i).getCond_param().equals(condParam)){
							isOk = true;
							break;
						}
					}
				}
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("为获取到数据库流程参数相应信息");
				return uocMessage;
			}
		}
		if(isOk){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("可以流转");
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("输入的流转参数与数据库配置不匹配");
			return uocMessage;
		}
	}

	/**
	 * 获取配置文件参数的值
	 * */
	public String getValueFromProperties(int modId) throws Exception{
		String value = "";
		
		if(modId == 0){
			value = propertiesParamVo.getCheckProcess_0();
		}
		if(modId == 1){
			value = propertiesParamVo.getCheckProcess_1();
		}
		return value;
	}
	
}

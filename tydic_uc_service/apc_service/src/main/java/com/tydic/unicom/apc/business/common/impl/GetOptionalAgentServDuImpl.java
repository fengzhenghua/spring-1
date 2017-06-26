package com.tydic.unicom.apc.business.common.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.apc.business.common.interfaces.GetOptionalAgentServDu;
import com.tydic.unicom.apc.business.common.vo.InfoAgentVo;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

import net.sf.json.JSONObject;

/**
 * 触点中心服务
 * APC0012-获取可选渠道<br>
 * @author ZhangCheng
 * @date 2017-04-14
 */
public class GetOptionalAgentServDuImpl implements GetOptionalAgentServDu{
	
	private static Logger logger = LoggerFactory.getLogger(GetOptionalDeveServDuImpl.class);

	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;

	@Override
	public UocMessage GetOptionalAgentInfo(InfoAgentVo infoAgentVo) throws Exception {
		
		UocMessage resultUocMessage = new UocMessage();
		
		if(logger.isInfoEnabled()){
			logger.info("[APC0012]==========>请求参数：jsession_id：{},chnl_code：{},"
				+ "chnl_name：{},region_id:{}",infoAgentVo.getJsession_id(),infoAgentVo.getChnl_code(),
				infoAgentVo.getChnl_name(),infoAgentVo.getRegion_id());
		}
		
		// 封装请求参数
		JSONObject requestJson = new JSONObject();
		
		Map<String,String> requestMap = new HashMap<String,String>();
		requestMap.put("jsession_id", infoAgentVo.getJsession_id());
		requestMap.put("chnl_code", infoAgentVo.getChnl_code());
		requestMap.put("chnl_name", infoAgentVo.getChnl_name());
		requestMap.put("region_id", infoAgentVo.getRegion_id());
		
		requestJson.put("param", requestMap);
		requestJson.put("SERVICE_NAME", "queryZbInfoAgents");
		
		try{
			// 通过能力平台服务调UAC认证中心服务-UAC0009-获取可选渠道服务
			resultUocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(requestJson.toString(), "", "UAC");
		} catch(Exception e){
			
			if(logger.isWarnEnabled()){
				logger.warn("[APC0012]==========>通过能力平台调认证中心获取可选渠道服务异常：{}",e);				
			}
			
			resultUocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			resultUocMessage.setContent("通过能力平台调认证中心获取可选渠道服务异常");
		}
		
		if(logger.isInfoEnabled()){
			logger.info("[APC0012]==========>响应参数：返回响应消息：{}",resultUocMessage.getContent());			
		}
		
		// 返回结果
		return resultUocMessage;
	}
		
}

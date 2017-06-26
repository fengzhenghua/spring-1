package com.tydic.unicom.apc.service.common.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.common.interfaces.GetIsLoginServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

import net.sf.json.JSONObject;

/**
 * 校验是否登录
 * @author ZhangCheng
 * @date 2017-03-10
 * @version V1.0
 *
 */
@Service("GetIsLoginServDu")
public class GetIsLoginServDuImpl implements GetIsLoginServDu {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GetIsLoginServDuImpl.class);
	
	@Autowired
	ApcAbilityPlatformServDu apcAbilityPlatformServDu;

	@Override
	public UocMessage GetIsLoginByJsessionId(String jsession_id) throws Exception {
		if(LOGGER.isInfoEnabled()){
			LOGGER.info("INFO [校验是否登录]==========>请求参数：{}",jsession_id);			
		}
		
		UocMessage respUocMessage = new UocMessage();
		
		// 参数校验
		if(StringUtils.isEmpty(jsession_id)){
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("请求参数中jsession_id不能为空");
			return respUocMessage;
		}
		
		// 封装请求参数
		JSONObject requestJson = new JSONObject();
		
		Map<String,String> requestMap = new HashMap<String,String>();
		requestMap.put("jsession_id", jsession_id);
		
		requestJson.put("param", requestMap);
		requestJson.put("SERVICE_NAME", "isLogin");
		
		try{
			// 通过能力平台服务调UAC认证中心服务-UAC0004-获取可选发展人接口
			respUocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(requestJson.toString(), "", "UAC");
		} catch(Exception e){
			LOGGER.warn("WARN [APC0003]==========>通过能力平台调认证中心校验登录接口异常：");
			e.printStackTrace();
			
			respUocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			respUocMessage.setContent("通过能力平台调认证中心校验登录接口异常");
			
		}
		if(!(RespCodeContents.SUCCESS.equals(respUocMessage.getRespCode())) ){
			respUocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			respUocMessage.setContent("通过能力平台调认证中心校验登录服务失败");
		}else{
			if("200".equals(respUocMessage.getArgs().get("code"))){
				respUocMessage.setRespCode(RespCodeContents.SUCCESS);
				respUocMessage.setContent("已登录");
			}else{
				respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
				respUocMessage.setContent("未登录");
			}
		}
		if(LOGGER.isInfoEnabled()){
			LOGGER.info("INFO [校验是否登录]==========>响应参数：{}",respUocMessage.toString());			
		}
		
		return respUocMessage;
	}

}

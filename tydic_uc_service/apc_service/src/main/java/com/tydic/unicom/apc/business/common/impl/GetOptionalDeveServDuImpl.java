package com.tydic.unicom.apc.business.common.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.apc.business.common.interfaces.GetOptionalDeveServDu;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.common.interfaces.GetIsLoginServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class GetOptionalDeveServDuImpl implements GetOptionalDeveServDu {

	private static Logger logger = LoggerFactory.getLogger(GetOptionalDeveServDuImpl.class);

	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;

	@Autowired
	private GetIsLoginServDu getIsLoginServDu;

	@Override
	public UocMessage getOptionalDeveInfo(String jsession_id, String developer_no, String developer_name,String region,String chnl_code) throws Exception{

		UocMessage resultUocMessage = new UocMessage();

		if(logger.isInfoEnabled()){
			logger.info("[APC0003]==========>请求参数：jsession_id：{},developer_no：{},"
				+ "developer_name：{},region:{},chnl_code:{}",jsession_id,developer_no,
				developer_name,region,chnl_code);
		}

		// 参数校验
		if(StringUtils.isEmpty(jsession_id)){
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("请求参数中jsession_id不能为空");
			return resultUocMessage;
		}

		// 校验是否登录
		resultUocMessage = getIsLoginServDu.GetIsLoginByJsessionId(jsession_id);
		if( !(RespCodeContents.SUCCESS.equals(resultUocMessage.getRespCode())) ){
			return resultUocMessage;
		}

		// 封装请求参数
		JSONObject requestJson = new JSONObject();

		Map<String,String> requestMap = new HashMap<String,String>();
		requestMap.put("jsession_id", jsession_id);
		requestMap.put("developer_no", developer_no);
		requestMap.put("developer_name", developer_name);
		requestMap.put("region", region);
		requestMap.put("chnl_code", chnl_code);

		requestJson.put("param", requestMap);
		requestJson.put("SERVICE_NAME", "queryInfoAgentDevelopers");

		try{
			// 通过能力平台服务调UAC认证中心服务-UAC0004-获取可选发展人接口
			resultUocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(requestJson.toString(), "", "UAC");
		} catch(Exception e){

			if(logger.isWarnEnabled()){
				logger.warn("[APC0003]==========>通过能力平台调认证中心获取可选发展人接口异常：{}",e);
			}

			resultUocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			resultUocMessage.setContent("通过能力平台调认证中心获取可选发展人接口异常");
		}

		if(logger.isInfoEnabled()){
			logger.info("[APC0003]==========>响应参数：返回响应消息：{}",resultUocMessage.getContent());
		}

		// 返回结果
		return resultUocMessage;
	}

	@Override
	public UocMessage getRewardDevelopInfo(String jsession_id, String developer_no) throws Exception {
		UocMessage resultUocMessage = new UocMessage();

		if (logger.isInfoEnabled()) {
			logger.info("[APC0017]==========>请求参数：jsession_id：" + jsession_id + ",developer_no：" + developer_no);
		}

		// 参数校验
		if (StringUtils.isEmpty(jsession_id) || StringUtils.isEmpty(developer_no)) {
			resultUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			resultUocMessage.setContent("请求参数为空");
			return resultUocMessage;
		}

		// 封装请求参数
		JSONObject requestJson = new JSONObject();

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("jsession_id", jsession_id);
		requestMap.put("developer_no", developer_no);

		requestJson.put("param", requestMap);
		requestJson.put("SERVICE_NAME", "getOptionalRewardOperInfo");

		try {
			// 通过能力平台服务调用认证中心服务UAC0010
			resultUocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(requestJson.toString(), "", "UAC");
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("[APC0017]==========>通过能力平台调认证中心获取可选激励发展人异常：{}", e);
			}

			resultUocMessage.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			resultUocMessage.setContent("通过能力平台调认证中心获取可选激励发展人异常");
		}

		return resultUocMessage;
	}

}

package com.tydic.unicom.uif.service.impl.ablit.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.tydic.unicom.uif.service.vo.AbilitReqestVo;
import com.tydic.unicom.uif.service.vo.CallLocalAbilitPlatVo;
import com.tydic.unicom.uif.service.vo.TapServiceVo;
import com.tydic.unicom.uif.service.vo.UifPropertiesParamVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午6:59:48
 * @ClassName CallLocalAbilitService
 * @Description 重庆本地接口能力平台调用 interface_type = 100
 * @version V1.0
 */
@Component("callLocalAbilitService")
public class CallType100ServiceImpl extends BaseCallLocalAbilityPlat {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final Integer connectTimeout = 40000;
	private final Integer readTimeout = 40000;
	
	private String serviceName;
	@Autowired
	@Qualifier("uifPropertiesParamVo")
	private UifPropertiesParamVo uifPropertiesParamVo;
	
	@Override
	public CallLocalAbilitPlatVo buildCallMessage(AbilitReqestVo requestVo) {
		CallLocalAbilitPlatVo callVo = new CallLocalAbilitPlatVo();
		
		TapServiceVo tapService = new TapServiceVo();
		tapService.setCommUrl(uifPropertiesParamVo.getLocalUrl());
		tapService.setToken(uifPropertiesParamVo.getLocalToken());
		tapService.setAppKey(uifPropertiesParamVo.getNewAppkey());
		tapService.setSecret(uifPropertiesParamVo.getSecret());
		tapService.setConnectTimeout(connectTimeout);
		tapService.setReadTimeout(readTimeout);
		
		callVo.setTapService(tapService);
		callVo.setInfoJson(requestVo.getJson_info());
		
		JSONObject data = JSONObject.parseObject(requestVo.getJson_info());
		serviceName = data.getString("SERVICE_NAME");
		
		return callVo;
	}
	
	@Override
	public UocMessage buildReturn(String returnMsg) {
		String code = null;
		UocMessage message = new UocMessage();
		logger.info("----------serviceName---------"+serviceName);
		JSONObject response = JSONObject.parseObject(returnMsg);
		if ("qryBusiFee".equals(serviceName)) {
			String respCode = "";
			if (response.get("RspCode") != null) {
				respCode = response.get("RspCode").toString();
			}
			if ("0000".equals(respCode)) {
				code = "200";
			} else {
				code = "9999";
			}
		} else
			if ("modRealNameCustInfo".equals(serviceName)) {
				String respCode = "";
				if (response.get("code") != null) {
					respCode = response.get("code").toString();
				}
				if ("0000".equals(respCode)) {
					code = "200";
				} else {
					code = "9999";
				}
				
			} else
				if ("get_card_data".equals(serviceName)) {
					String respCode = "";
					if (response.get("status") != null) {
						respCode = response.get("status").toString();
					}
					if ("1".equals(respCode)) {
						code = "200";
					} else {
						code = "9999";
					}
					
				} else {
					String respCode = "";
					if (response.get("RespCode") != null) {
						respCode = response.get("RespCode").toString();
					}
					if ("0000".equals(respCode)) {
						code = "200";
					} else {
						code = "9999";
					}
				}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("调用能力平台成功");
		message.addArg("json_info", returnMsg);
		message.addArg("code", code);
		return message;
	}
	
}

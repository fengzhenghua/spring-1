package com.tydic.unicom.uif.service.impl.ablit.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
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
 * @ClassName callHdAbilitService
 * @Description 重庆本地接口能力平台调用 interface_type = 700，701,702,703
 * @version V1.0
 */
@Component("callHdAbilitService")
public class CallTypePrefix700ServiceImpl extends BaseCallLocalAbilityPlat{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Integer connectTimeout = 40000;
	private final Integer readTimeout = 40000;
	private String interfaceType;
	@Autowired
	@Qualifier("uifPropertiesParamVo")
	private UifPropertiesParamVo uifPropertiesParamVo;
	
	@Override
	public CallLocalAbilitPlatVo buildCallMessage(AbilitReqestVo requestVo) {
		CallLocalAbilitPlatVo callVo = new CallLocalAbilitPlatVo();
		TapServiceVo tapService = new TapServiceVo();
		JSONObject json= new JSONObject();
		interfaceType=requestVo.getInterface_type();
		String interfaceUrl=requestVo.getInterface_url();
		if("700".equals(interfaceType) || "702".equals(interfaceType)){
			tapService.setCommUrl(uifPropertiesParamVo.getHdUrl());
			tapService.setToken(uifPropertiesParamVo.getHdToken());
			json.put("sendUrl", interfaceUrl);
			json.put("params", JSON.parse(requestVo.getJson_info()));
		}else if("701".equals(interfaceType) || "703".equals(interfaceType)){
			tapService.setCommUrl(uifPropertiesParamVo.getLocalUrl());
			tapService.setToken(uifPropertiesParamVo.getLocalToken());
			JSONObject object = JSONObject.parseObject(requestVo.getJson_info());
			String ServiceSn=object.get("order_no").toString();
			json.put("SERVICE_NAME", interfaceUrl);
			json.put("any_dog4", ServiceSn);
			json.put("any_dog",  "returncheck");
			json.put("any_dog1",  "78");
			json.put("any_dog3", "system");
			if("701".equals(interfaceType)){
				String tacheCode=object.get("tache_code").toString();
				if("S00010".equals(tacheCode)){
					json.put("any_dog2", "1");
				}else{
					json.put("any_dog2", "0");
				}
			}else{
				String next_step=object.get("next_step").toString(); //处理的状态
				json.put("any_dog2", next_step);
			}			
		}else{
			return null;
		}		
		tapService.setAppKey(uifPropertiesParamVo.getNewAppkey());
		tapService.setSecret(uifPropertiesParamVo.getSecret());
		tapService.setConnectTimeout(connectTimeout);
		tapService.setReadTimeout(readTimeout);
		
		callVo.setTapService(tapService);
		callVo.setInfoJson(json.toString());
		return callVo;
	}

	@Override
	public UocMessage buildReturn(String returnMsg) {
		String code = null;
		UocMessage message = new UocMessage();
		logger.info("----------interfaceType---------"+interfaceType);
		JSONObject response = JSONObject.parseObject(returnMsg);
		if("700".equals(interfaceType) || "701".equals(interfaceType) || "703".equals(interfaceType)){
			code="200";
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("调用能力平台成功");
			message.addArg("json_info", returnMsg);
			message.addArg("code", code);
		}else if("702".equals(interfaceType)){
			String respCode="";
			if(response.get("respCode") != null){
				respCode = response.get("respCode").toString();
			}
			if("0000".equals(respCode)){
				code="200";
			}else{
				code="9999";
			}
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("调用能力平台成功");
			message.addArg("json_info", returnMsg);
			message.addArg("code", code);
		}else{
			code="9999";
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("無該類型的interfaceType");
			message.addArg("json_info", returnMsg);
			message.addArg("code", code);
		}
		return message;
	}

}

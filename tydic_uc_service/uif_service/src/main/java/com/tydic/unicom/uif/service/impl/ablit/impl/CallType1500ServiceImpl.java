package com.tydic.unicom.uif.service.impl.ablit.impl;

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
 * @author yangxiaofeng 2017年5月17日 下午6:59:48
 * @ClassName callSendMessageAbilitService
 * @Description 重庆本地接口能力平台调用 interface_type = 1500
 * @version V1.0
 */
@Component("callSendMessageAbilitService")
public class CallType1500ServiceImpl extends BaseCallLocalAbilityPlat{

	private final Integer connectTimeout = 40000;
	private final Integer readTimeout = 40000;
	
	@Autowired
	@Qualifier("uifPropertiesParamVo")
	private UifPropertiesParamVo uifPropertiesParamVo;
	
	@Override
	public CallLocalAbilitPlatVo buildCallMessage(AbilitReqestVo requestVo) {
		CallLocalAbilitPlatVo callVo = new CallLocalAbilitPlatVo();
		TapServiceVo tapService = new TapServiceVo();
		tapService.setCommUrl(uifPropertiesParamVo.getSendMessageUrl());
		tapService.setToken(uifPropertiesParamVo.getSendMessageToken());
		tapService.setAppKey(uifPropertiesParamVo.getNewAppkey());
		tapService.setSecret(uifPropertiesParamVo.getSecret());
		tapService.setConnectTimeout(connectTimeout);
		tapService.setReadTimeout(readTimeout);
		
		callVo.setTapService(tapService);
		callVo.setInfoJson(requestVo.getJson_info());
		return callVo;
	}

	@Override
	public UocMessage buildReturn(String returnMsg) {
		String code = null;
		UocMessage message = new UocMessage();
		JSONObject response = JSONObject.parseObject(returnMsg);
		if("1".equals(response.get("status").toString())){
			code="200";
		}else{
			code="9999";
		}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("调用能力平台成功");
		message.addArg("json_info", returnMsg);
		message.addArg("code", code);
		return message;
	}
	
	

}

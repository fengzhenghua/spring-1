package com.tydic.unicom.uif.service.impl.ablit.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
 * @author heguoyong 2017年5月17日 下午9:52:27
 * @ClassName CallType200ServiceImpl
 * @Description 调用AOP能力
 * @version V1.0
 */
@Component("callAopAbilitService")
public class CallType200ServiceImpl extends BaseCallLocalAbilityPlat {
	
	private final Integer connectTimeout = 40000;
	private final Integer readTimeout = 40000;
	
	@Autowired
	@Qualifier("uifPropertiesParamVo")
	private UifPropertiesParamVo uifPropertiesParamVo;
		
	@Override
	public CallLocalAbilitPlatVo buildCallMessage(AbilitReqestVo requestVo) {
		CallLocalAbilitPlatVo callVo = new CallLocalAbilitPlatVo();
		TapServiceVo tapService = new TapServiceVo();
		tapService.setCommUrl(uifPropertiesParamVo.getAopUrl());
		tapService.setToken(uifPropertiesParamVo.getAopToken());
		tapService.setAppKey(uifPropertiesParamVo.getNewAppkey());
		tapService.setSecret(uifPropertiesParamVo.getSecret());
		tapService.setConnectTimeout(connectTimeout);
		tapService.setReadTimeout(readTimeout);
		callVo.setTapService(tapService);
		String method="";
		String bizkey="";
		if(requestVo.getInterface_param_json()!=null && !"".equals(requestVo.getInterface_param_json())){
		   JSONObject object = JSONObject.parseObject(requestVo.getInterface_param_json());
		   method=object.get("method").toString();
		   bizkey=object.get("bizkey").toString();
		}else{
			return null;
		}
		SimpleDateFormat sm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp1 = sm1.format(new Date());
		String apptx = String.valueOf(apptx(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase())).replaceAll(
				"-", "");
		JSONObject json= new JSONObject();
		json.put("method", method);
		json.put("timestamp", timestamp1);
		json.put("sign", "1");
		json.put("apptx", apptx);
		json.put("bizkey", bizkey);
		json.put("msg", JSON.parse(requestVo.getJson_info()));		
		callVo.setInfoJson(json.toString());
		callVo.setTapService(tapService);	
		return callVo;
	}
	
	@Override
	public UocMessage buildReturn(String returnMsg) {
		
		String code = null;
		UocMessage message = new UocMessage();
		JSONObject response = JSONObject.parseObject(returnMsg);
		if(response.get("code") !=null && !"".equals(response.get("code").toString())){
			code=response.get("code").toString();
		}else{
			code="200";
		}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("调用能力平台成功");
		message.addArg("json_info", returnMsg);
		message.addArg("code", code);
		return message;
	}
	
	
	private Long apptx(String s) {
		Long sum = 0l;
		int tmp = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				tmp = c - '0';
			} else
				if (c >= 'A' && c <= 'F') {
					tmp = c - 'A' + 10;
				} else {
					break;
				}
			sum = sum * 16 + tmp;
		}
		return sum;
	}
	
}

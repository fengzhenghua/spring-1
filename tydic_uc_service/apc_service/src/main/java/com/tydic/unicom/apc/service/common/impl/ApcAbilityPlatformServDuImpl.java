package com.tydic.unicom.apc.service.common.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.util.HttpUtil;
import com.tydic.unicom.webUtil.BusiMessage;
import com.tydic.unicom.webUtil.FastJsonUtils;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UIFMessage;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ApcAbilityPlatformServDu")
public class ApcAbilityPlatformServDuImpl implements ApcAbilityPlatformServDu{
	
	Logger logger = Logger.getLogger(ApcAbilityPlatformServDuImpl.class);
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Value("${uif.service.url}")
	private String url;
	
	@Override
	public UocMessage CallApcAbilityPlatform(String json_info,String interface_code,String center_code)throws Exception{
		

		logger.info("*******************apc http调用入参json_info***********************"+json_info);
		logger.info("*******************apc http调用入参center_code***********************"+center_code);
		
		UocMessage message= new UocMessage();

		if("".equals(json_info) || json_info==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("center_code为必传参数");
			return message;
		}
		
		if("".equals(center_code) || center_code==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("center_code为必传参数");
			return message;
		}
		
		
		JSONObject data = new JSONObject();	
		data.put("fromCenterCode", "UGC");
		
		if("UAC".equals(center_code)){//UAC认证中心服务
			data.put("toCenterCode", "UAC");
		}else if("UGC".equals(center_code)){//UGC商品中心服务
			data.put("toCenterCode", "UGC");
		}else if("URC".equals(center_code)){//URC资源中心服务
			data.put("toCenterCode", "URC");
		}else if("UOC".equals(center_code)){//UOC订单中心服务
			data.put("toCenterCode", "UOC");
		}else if("UIF".equals(center_code)){//UOC订单中心服务
			data.put("toCenterCode", "ABILIT");
		}else{
			logger.info("不支持的中心接口调用center_code"+center_code);
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("不支持的中心接口调用");
			return message;
		}		
		JSONObject json=JSONObject.parseObject(json_info);	
		data.put("params", json.get("params"));
		logger.info("apc HTTP调用json_info=" + data.toString());
		String dataStr = "json_info=" + String.valueOf(data.toString());
		BusiMessage<String> response = HttpUtil.formHttpPost(url, dataStr);
		logger.info("-----------apc http调用返回报文-----------");
		logger.info(response.toString());
		logger.info("-----------apc http调用返回报文-----------");
		if (response.getSuccess()) {
			//将null值转换为空字符串
			String asEmptyStr=FastJsonUtils.parserNullStringAsEmpty(response.getData());		
			String code=null;
			UIFMessage uocMessage = JSON.parseObject(String.valueOf(asEmptyStr),UIFMessage.class);
			if("0000".equals(uocMessage.getRespCode().toString())){
				code="200";
			}else{
				code="9999";
			}
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("apc http调用成功");
			message.addArg("json_info", uocMessage.getArgs().toString());
			message.addArg("code", code);	
		}else{
			message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			message.setContent("apc http调用失败");
		}
		return message;
	}

}

package com.tydic.unicom.ugc.service.common.impl;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.unicom.ugc.service.common.interfaces.UgcAbilityPlatformServDu;
import com.tydic.unicom.ugc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.util.HttpUtil;
import com.tydic.unicom.webUtil.BusiMessage;
import com.tydic.unicom.webUtil.FastJsonUtils;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UIFMessage;
import com.tydic.unicom.webUtil.UocMessage;

@Service("UgcAbilityPlatformServDu")
public class UgcAbilityPlatformServDuImpl implements UgcAbilityPlatformServDu{

    Logger logger = Logger.getLogger(UgcAbilityPlatformServDuImpl.class);

	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	@Value("${uif.service.url}")
	private String url;

	@Override
	public UocMessage CallUgcAbilityPlatform(String json_info,String interface_code,String center_code)throws Exception{
		
		logger.info("*******************ugc http调用入参json_info***********************"+json_info);
		logger.info("*******************ugc http调用入参center_code***********************"+center_code);		
		UocMessage message= new UocMessage();

		if("".equals(json_info) || json_info==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("json_info为必传参数");
			return message;
		}		

		
		if("".equals(center_code) || center_code==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("center_code为必传参数");
			return message;
		}		

		JSONObject data = new JSONObject();
		data.put("fromCenterCode", "UGC");
		data.put("toCenterCode", center_code);
		data.put("params", json_info);
		logger.info("ugc HTTP调用json_info=" + data.toString());
		String dataStr = "json_info=" + String.valueOf(data.toString());
		BusiMessage<String> response = HttpUtil.formHttpPost(url, dataStr);
		logger.info("-----------ugc http调用返回报文-----------");
		logger.info(response.toString());
		logger.info("-----------ugc http调用返回报文-----------");
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
			message.setContent("ugc http调用成功");
			message.addArg("json_info", uocMessage.getArgs().toString());
			message.addArg("code", code);			
		}else{
			message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			message.setContent("ugc http调用失败");
		}

		return message;

	}

}

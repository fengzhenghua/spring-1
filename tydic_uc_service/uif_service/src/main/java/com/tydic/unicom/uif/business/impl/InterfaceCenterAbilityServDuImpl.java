package com.tydic.unicom.uif.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.tydic.unicom.uif.business.interfaces.InterfaceCenterAbilityServDu;
import com.tydic.unicom.uif.business.vo.CallUIFCenterRequsetVo;
import com.tydic.unicom.uif.service.interfaces.ICallSystemByUifServ;
import com.tydic.unicom.uif.service.vo.EnSystemHandler;
import com.tydic.unicom.util.EnumHelper;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;


public class InterfaceCenterAbilityServDuImpl implements InterfaceCenterAbilityServDu{
	
	@Autowired
	private ApplicationContext context;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public UocMessage CreateAbilityPlatform(String json_info)throws Exception{
		UocMessage message= new UocMessage();

		if(json_info==null ||  "".equals(json_info)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("json_info不能为空");
			return message;
		}
		CallUIFCenterRequsetVo requsetVo = JSON.parseObject(json_info, CallUIFCenterRequsetVo.class);
		ICallSystemByUifServ handler = null;
		EnSystemHandler eunumType = EnumHelper.getEnum(requsetVo.getToCenterCode(), EnSystemHandler.class);
		String handlerName = eunumType.getHandler(); 
		List<String> params = eunumType.getucSystemParms();
		
		if(StringUtils.isEmpty(handlerName)){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("不支持的中心调用");
			message.addArg("code", "9999");
		}
		try {
			handler = (ICallSystemByUifServ)context.getBean(handlerName);
			message = handler.callOtherSystem(requsetVo.getParams(),params);
		} catch (Exception e) {
			logger.error("调用接口异常"+e.getMessage(),e);
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("调用接口异常");
			message.addArg("code", "9999");
			return message;
		}
		
		return message;
		
	}

}

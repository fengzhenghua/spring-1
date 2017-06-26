package com.tydic.unicom.uif.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.tydic.unicom.aspect.annotation.SystemAuditLog;
import com.tydic.unicom.uif.service.interfaces.IAbstractInterfaceTypeService;
import com.tydic.unicom.uif.service.interfaces.ICallSystemByUifServ;
import com.tydic.unicom.uif.service.vo.AbilitReqestVo;
import com.tydic.unicom.uif.service.vo.EnInterfaceType;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Component("callAbilitCenterHandler")
public class CallAbilitCenterHandler implements ICallSystemByUifServ {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ApplicationContext context;
	
	@Override
	@SystemAuditLog
	public UocMessage callOtherSystem(String paramsValueJson, List<String> params) {
		logger.info("进入调用Abilit服务,入参" + paramsValueJson);
		UocMessage message = new UocMessage();		
		AbilitReqestVo requestVo = JSON.parseObject(paramsValueJson, AbilitReqestVo.class);		
		IAbstractInterfaceTypeService interfaceTypeService = null;
		EnInterfaceType  InterfaceType = EnInterfaceType.getEnInterfaceTypeByType(requestVo.getInterface_type());
		String interfaceName = InterfaceType.getInterfaceName();
		if(StringUtils.isEmpty(interfaceName)){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("不支持的接口类型");
			message.addArg("code", "9999");
		}
		try {
			interfaceTypeService = (IAbstractInterfaceTypeService)context.getBean(interfaceName);
			message = interfaceTypeService.getAblitReturn(requestVo);
		} catch (Exception e) {
			logger.error("调用接口异常"+e.getMessage(),e);
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("调用能力平台接口异常");
			message.addArg("code", "9999");
			return message;
		}
		return message;
	}
	
}

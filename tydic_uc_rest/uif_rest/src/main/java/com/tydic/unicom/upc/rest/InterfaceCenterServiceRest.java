package com.tydic.unicom.upc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uif.business.interfaces.InterfaceCenterAbilityServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.INTERFACE_CENTER_REST)
public class InterfaceCenterServiceRest {
	
	Logger logger = Logger.getLogger(InterfaceCenterServiceRest.class);

	@Autowired
	private InterfaceCenterAbilityServDu interfaceCenterAbilityServDu;

	@RequestMapping(value = UrlsMappings.UIF_ABILITY_PLATFORM_SERVICE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage AbilityPlatformForUif(String json_info){

		UocMessage message = new UocMessage();

		logger.info("===json_info===="+json_info);
		try {
			message = interfaceCenterAbilityServDu.CreateAbilityPlatform(json_info);
			logger.info("======================success============================");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("======================exception============================");
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("调用接口中心异常");
			message.addArg("code", "9999");
			return message;
		}
	}

}

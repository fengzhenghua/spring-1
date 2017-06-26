package com.tydic.unicom.ugc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.ugc.business.common.interfaces.AbilityPlatformForUgcServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;



@Controller
@RequestMapping(value = ControllerMappings.COMMODITY_CENTER_REST)
public class CommodityCenterServiceRest {
	
	Logger logger = Logger.getLogger(CommodityCenterServiceRest.class);

	@Autowired
	private AbilityPlatformForUgcServDu abilityPlatformForUgcServDu;

	@RequestMapping(value = UrlsMappings.COMMODITY_CENTER_SERVICE , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage AbilityPlatformForYunWo(String json_info){

		UocMessage message = new UocMessage();

		logger.info("===json_info===="+json_info);
		try {
			message = abilityPlatformForUgcServDu.createPlatformForUgc(json_info);
			logger.info("======================success============================");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("======================exception============================");
			message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			message.setContent("通过能力平台调用商品中心接口异常");
			return message;
		}
	}
	

}

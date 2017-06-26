package com.tydic.unicom.apc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.apc.business.common.interfaces.GetInfoServDu;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.GET_INFO_REST)
public class GetInfoRest {

	@Autowired
	private GetInfoServDu getInfoServDu;
	
	private static Logger logger = Logger.getLogger(GetInfoRest.class);
	
	@RequestMapping(value = UrlsMappings.GET_BUSINESS_HALL_INFO,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage getBusinessHallInfo(String jsessionId,String areaId,String departNo,String departName){
		logger.info("===========================获取可选营业厅信息（apc）========================");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = getInfoServDu.getBusinessHallInfo(jsessionId, areaId, departNo, departName);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("获取可选营业厅信息异常");
			return uocMessage;
		}
		return uocMessage;
		
	}
}

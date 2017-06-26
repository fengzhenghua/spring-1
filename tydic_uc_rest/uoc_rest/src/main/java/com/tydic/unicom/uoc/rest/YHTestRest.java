package com.tydic.unicom.uoc.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.test.interfaces.YHTestBaseServiceServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.TEST_REST)
public class YHTestRest {

	private static Logger logger = Logger.getLogger(YHTestRest.class);
	
	@Autowired
	private YHTestBaseServiceServDu yHTestBaseServiceServDu;
	
	
	@RequestMapping(value = UrlsMappings.TEST_URL , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage Test(String serv_order_no, String order_type,String jsession_id){
		UocMessage uocmessage = new UocMessage();
		uocmessage = yHTestBaseServiceServDu.testGetIdServDu(serv_order_no, order_type,jsession_id);
		return uocmessage;
	}
	
}

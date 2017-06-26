package com.tydic.unicom.apc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.apc.business.pub.interfaces.OppoUocOrderServiceServDu;
import com.tydic.unicom.apc.business.pub.vo.ApcUocOrderVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.OPPO_UOC_ORDER_SERVICE_REST)
public class OppoUocOrderServiceRest {

	private static Logger logger = Logger.getLogger(OppoUocOrderServiceRest.class);
	
	@Autowired
	private OppoUocOrderServiceServDu oppoUocOrderServiceServDu;
	@RequestMapping(value = UrlsMappings.CREATE_UOC_ORDER,method=RequestMethod.POST)
	@ResponseBody
	public UocMessage createUocOrder(ApcUocOrderVo apcUocOrderVo){
		logger.info("==============创建中台订单===============");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoUocOrderServiceServDu.createUocOrder(apcUocOrderVo);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("创建中台订单异常");
			return uocMessage;
		}	
	}
}

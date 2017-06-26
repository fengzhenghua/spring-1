package com.tydic.unicom.uoc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.order.service.interfaces.WriteCardResultServDu;
import com.tydic.unicom.uoc.business.order.service.vo.WriteCardResultInVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.WRITE_CARD_RESULT_REST)
public class WriteCardResultRest {
	
	Logger logger = Logger.getLogger(WriteCardResultRest.class);
	
	@Autowired
	private WriteCardResultServDu writeCardResultServDu;
	
	@RequestMapping(value = UrlsMappings.GET_WRITE_CARD_RESULT , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getWriteCardResult(WriteCardResultInVo vo){
		logger.info("rest-----------queryWriteCardResult");
		UocMessage message =new UocMessage();
		try {
			message = writeCardResultServDu.queryWriteCardResult(vo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("写卡结果查询异常");
			return message;
		}

		return message;

	}
	
	@RequestMapping(value = UrlsMappings.CREATE_WRITE_CARD_RESULT , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage createWriteCardResult(WriteCardResultInVo vo){
		logger.info("rest-----------createWriteCardResult");
		UocMessage message =new UocMessage();
		try {
			message = writeCardResultServDu.createWriteCardResult(vo);
		} catch (UocException e) {
			e.printStackTrace();
			return e.getUocMessage();
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("写卡结果记录异常");
			return message;
		}

		return message;

	}

}

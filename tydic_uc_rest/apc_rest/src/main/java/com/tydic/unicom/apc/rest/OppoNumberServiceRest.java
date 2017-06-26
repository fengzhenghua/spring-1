package com.tydic.unicom.apc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.apc.business.ofr.interfaces.OppoNumberServiceServDu;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.OPPO_NUMBER_SERVICE_REST)
public class OppoNumberServiceRest {

	private static Logger logger = Logger.getLogger(OppoNumberServiceRest.class);
	
	@Autowired
	private OppoNumberServiceServDu oppoNumberServiceServDu;
	
	@RequestMapping(value = UrlsMappings.OPPO_SELECT_NUMBER,method = RequestMethod.POST)
	@ResponseBody
	public UocMessage oppoSelectNumber(String oper_no,String fuzzy_query,String page_info,String tele_type,String ser_type,String good_num_flag){
		logger.info("============(oppo)选号==========");
		UocMessage uocMessage = new UocMessage();
		
		try {
			uocMessage = oppoNumberServiceServDu.oppoSelectNumber(oper_no, fuzzy_query, page_info, tele_type, ser_type,good_num_flag);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("选号异常");
			return uocMessage;
		}	
	}
	
	@RequestMapping(value = UrlsMappings.OPPO_OCCUPY_NUMBER,method = RequestMethod.POST)
	@ResponseBody
	public UocMessage oppoOccupyNumber(String oper_no,String acc_nbr,String ser_type,String tele_type,String occupiedFlag){
		logger.info("============(oppo)号码预占==========");
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = oppoNumberServiceServDu.oppoOccupyNumber(oper_no, acc_nbr, ser_type, tele_type, occupiedFlag);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("号码预占异常");
			return uocMessage;
		}
	}

}

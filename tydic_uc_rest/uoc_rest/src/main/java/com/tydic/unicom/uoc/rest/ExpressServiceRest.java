package com.tydic.unicom.uoc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.uoc.business.common.interfaces.ExpressServDu;
import com.tydic.unicom.uoc.business.common.vo.ExpressVo;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.EXPRESS_SERVICE)
public class ExpressServiceRest {
	
	Logger logger = Logger.getLogger(ExpressServiceRest.class);
	
	@Autowired
	private ExpressServDu expressServDu;
	/**
	 * ⦁	获取顺丰纸质单信息 ⦁	接口编号UOC0039
	 */
	@RequestMapping(value = UrlsMappings.GET_INFO_FOR_SF , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getInfoForSF(ParaVo paraVo){
		logger.info("rest-----------getInfoForSF");
		UocMessage message =new UocMessage();
		try {
			message = expressServDu.getPaperInfoForSF(paraVo);
		} 
		catch (UocException e){
			e.printStackTrace();
			return e.getUocMessage();
		}
		catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("获取顺丰纸质单信息异常");
			return message;
		}
		return message;
	}
	
	/**
	 * 保存默认寄件信息  UOC0040
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.SAVE_SEND_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage saveSendInfo(ExpressVo vo){
		logger.info("rest-----------saveSendInfo");
		UocMessage message =new UocMessage();
		try {
			message = expressServDu.saveSendInfo(vo);
		} 
		catch (UocException e){
			e.printStackTrace();
			return e.getUocMessage();
		}
		catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("保存默认寄件信息异常");
			return message;
		}
		return message;
	}
	
	/**
	 * ⦁	获取默认寄件信息 ⦁	接口编号UOC0043
	 */
	@RequestMapping(value = UrlsMappings.GET_SEND_INFO , method = RequestMethod.POST)
	@ResponseBody
	public UocMessage getSendInfo(ExpressVo vo){
		logger.info("rest-----------getSendInfo");
		UocMessage message =new UocMessage();
		try {
			message = expressServDu.getSendInfo(vo);
		} 
		catch (UocException e){
			e.printStackTrace();
			return e.getUocMessage();
		}
		catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("获取默认寄件信息异常");
			return message;
		}
		return message;
	}

}

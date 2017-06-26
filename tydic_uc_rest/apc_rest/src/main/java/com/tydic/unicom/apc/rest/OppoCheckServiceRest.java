package com.tydic.unicom.apc.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.apc.business.ofr.interfaces.OppoCheckServiceServDu;
import com.tydic.unicom.apc.business.pub.vo.CheckUserInfoVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Controller
@RequestMapping(value = ControllerMappings.OPPO_CHECK_SERVICE_REST)
public class OppoCheckServiceRest {

	@Autowired
	private OppoCheckServiceServDu oppoCheckServiceServDu;

	private static Logger logger = Logger.getLogger(OppoCheckServiceRest.class);

	@RequestMapping(value = UrlsMappings.CHECK_GZT,method = RequestMethod.POST)
	@ResponseBody
	public UocMessage checkGZT(String certName, String certId,String flag) {
		UocMessage uocMessage = new UocMessage();
		logger.info("-----------rest  国政通校验---------");
		try {
			uocMessage = oppoCheckServiceServDu.checkGZT(certName, certId,flag);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("国政通校验异常");
			return uocMessage;
		}
		return uocMessage;
	}

	@RequestMapping(value = UrlsMappings.CHECK_CUST_INFO,method = RequestMethod.POST)
	@ResponseBody
	public UocMessage checkCustInfo(CheckUserInfoVo vo){
		UocMessage uocMessage = new UocMessage();
		logger.info("-----------rest  客户资料校验---------");
		try {
			uocMessage = oppoCheckServiceServDu.checkCustInfo(vo);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("客户资料校验异常");
			return uocMessage;
		}
		return uocMessage;
	}

	@RequestMapping(value = UrlsMappings.QUERY_USER_NUMBER,method = RequestMethod.POST)
	@ResponseBody
	public UocMessage queryUserNumber(String oper_no, String certId,String certName){
		UocMessage uocMessage = new UocMessage();
		logger.info("-----------rest  一证五户校验---------");
		try {
			uocMessage = oppoCheckServiceServDu.queryUserNumber(oper_no, certId, certName);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("客户资料校验异常");
			return uocMessage;
		}
		return uocMessage;
	}

	@RequestMapping(value = UrlsMappings.CHECK_CUST_NUMS_FORAP, method = RequestMethod.POST)
	@ResponseBody
	public UocMessage checkCertNumsForAp(String oper_no, String ap_id, String cert_no) {
		UocMessage uocMessage = new UocMessage();
		logger.info("-----------rest  身份证开卡数量检验---------");
		try {
			uocMessage = oppoCheckServiceServDu.checkCertNumsForAp(oper_no, ap_id, cert_no);
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("身份证开卡数量检验异常");
			return uocMessage;
		}
		return uocMessage;
	}

}

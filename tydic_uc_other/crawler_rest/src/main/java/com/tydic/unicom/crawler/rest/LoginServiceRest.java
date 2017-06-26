package com.tydic.unicom.crawler.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.unicom.crawler.business.interfaces.LoginServiceBus;
import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = ControllerMappings.LOGIN_SERVICE_REST)
public class LoginServiceRest {

	private static Logger logger = Logger.getLogger(LoginServiceRest.class);
	
	@Autowired
	private LoginServiceBus loginServiceBus;
	
	@RequestMapping(value = UrlsMappings.LOGIN , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage login(String json_info){
		BusRespMessage message = new BusRespMessage();
		logger.info("[BEG]得到登录参数："+json_info);
		try {
		JSONObject json = JSONObject.fromObject(json_info);
		BusinessResParamVo brpv = new BusinessResParamVo();
		brpv.setUser(json.getString("user"));
		brpv.setPassword(json.getString("pwd"));
		brpv.setSafecode(json.getString("safecode"));

			message = loginServiceBus.loginMethod(brpv);
			logger.info("[END]登录完成:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("登录异常");
			logger.info("[ERR]登录异常:"+e.getMessage());
			return message;
		}
	}
	
	@RequestMapping(value = UrlsMappings.IS_LOGIN , method = RequestMethod.POST)
	@ResponseBody
	public BusRespMessage isLogin(String json_info){
		BusRespMessage message = new BusRespMessage();
		logger.info("[BEG]登录校验异常："+json_info);
		try {
			JSONObject json = JSONObject.fromObject(json_info);
			BusinessResParamVo brpv = new BusinessResParamVo();
			brpv.setUser(json.getString("user"));
			brpv.setPassword(json.getString("pwd"));

			message = loginServiceBus.isLoginMethod(brpv);
			logger.info("[END]登录校验完成:"+message.toString());
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(BusRespMessage.SYSTEM_EXCEPTION);
			message.setContent("登录校验异常");
			logger.info("[ERR]登录校验异常:"+e.getMessage());
			return message;
		}
	}
}

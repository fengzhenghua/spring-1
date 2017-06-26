package com.tydic.unicom.crawler.business.interfaces;

import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;


public interface LoginServiceBus {

	/**
	 * 登录
	 * */
	public BusRespMessage loginMethod(BusinessResParamVo businessResParamVo) throws Exception;
	
	/**
	 * 是否登录
	 * */
	public BusRespMessage isLoginMethod(BusinessResParamVo businessResParamVo) throws Exception;
}

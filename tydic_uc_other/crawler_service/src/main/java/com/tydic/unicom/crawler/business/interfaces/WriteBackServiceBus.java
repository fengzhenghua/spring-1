package com.tydic.unicom.crawler.business.interfaces;

import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;

public interface WriteBackServiceBus {

	/**
	 * 回写手工开户
	 * */
	public BusRespMessage writeBackManualAccountMethod(BusinessResParamVo businessResParamVo) throws Exception;
	
	/**
	 * 回写快递
	 * */
	public BusRespMessage writeBackExpressMethod(BusinessResParamVo businessResParamVo) throws Exception;

	
	/**
	 * 中台回调
	 * */
	public BusRespMessage crawlerRecallMethod(BusinessResParamVo businessResParamVo) throws Exception;
	
	/**
	 * 退单
	 * */
	public BusRespMessage crawlerCancelApplyMethod(BusinessResParamVo businessResParamVo) throws Exception;

	/**
	 * 更新订单状态
	 * */
	public BusRespMessage updateOrderStatusMethod(BusinessResParamVo businessResParamVo) throws Exception;
	
	
	
}

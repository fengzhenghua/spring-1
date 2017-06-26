package com.tydic.unicom.crawler.business.interfaces;

import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;

public interface CrawlerServiceBus {

	/**
	 * 抓取手工开户
	 * */
	public BusRespMessage crawlerManualAccountMethod(BusinessResParamVo businessResParamVo) throws Exception;
	
	/**
	 * UOC订单创建
	 * */
	public BusRespMessage crawlerManualAccountCreateOrder(BusinessResParamVo businessResParamVo) throws Exception;
	
	/**
	 * 爬虫，创建商品信息
	 * */
	public BusRespMessage createSKU(BusinessResParamVo businessResParamVo) throws Exception;
	/**
	 * 修改商品信息
	 * */
	public BusRespMessage updateSKU(BusinessResParamVo businessResParamVo) throws Exception;

	/**
	 * 得到商品信息
	 * */
	public BusRespMessage selectSKU(BusinessResParamVo businessResParamVo) throws Exception;

}

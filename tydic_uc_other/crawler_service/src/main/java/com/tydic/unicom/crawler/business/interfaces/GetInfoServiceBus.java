package com.tydic.unicom.crawler.business.interfaces;

import com.tydic.unicom.crawler.business.vo.BusRespMessage;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;

public interface GetInfoServiceBus {
	/**
	 * 得到验证码
	 * */
	public BusRespMessage getSafeCode(BusinessResParamVo businessResParamVo) throws Exception;
	
	/**
	 * 初始化系统参数
	 * */
	public BusRespMessage initSysParamsMethod(BusinessResParamVo businessResParamVo) throws Exception;
 
	/**
	 * 通过SESSIOID 得到爬虫的用户名
	 * */
	public BusRespMessage getUserNameMethod(BusinessResParamVo businessResParamVo) throws Exception;
	
	/**
	 * 得到订单列表订单信息
	 * @param businessResParamVo
	 * @return
	 * @throws Exception
	 */
	public BusRespMessage getOrderInfoList(BusinessResParamVo businessResParamVo) throws Exception;
	
	/**
	 * 得到订单信息
	 * @param businessResParamVo
	 * @return
	 * @throws Exception
	 */
	public BusRespMessage getOrderInfoItem(BusinessResParamVo businessResParamVo) throws Exception;
	
	
	/**
	 * 得到状态数据
	 * @param businessResParamVo
	 * @return
	 * @throws Exception
	 */
	public BusRespMessage getCountStatus(BusinessResParamVo businessResParamVo) throws Exception;
	
	
}

package com.tydic.unicom.apc.business.common.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface GetInfoServDu {

	/**
	 * 获取可选营业厅信息
	 * */
	public UocMessage getBusinessHallInfo(String jsessionId,String areaId,String departNo,String departName) throws Exception;
	
	/**
	 * 获取工号信息
	 * */
	public UocMessage getOperInfo(String jsessionId) throws Exception;
}

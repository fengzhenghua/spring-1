package com.tydic.unicom.uoc.service.activiti.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface ProcessHistoryServDu {

	/**
	 * 获取流程实例历史信息
	 * */
	public UocMessage findProcessHistoryInfo(String processInstanceId,String orderNo) throws Exception;
}

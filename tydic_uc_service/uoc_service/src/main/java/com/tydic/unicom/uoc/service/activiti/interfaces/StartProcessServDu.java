package com.tydic.unicom.uoc.service.activiti.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface StartProcessServDu {

	/**
	 * 根据传入的订单号跟流转类型启动流程
	 */
	public UocMessage startProcess(String order_no,String oper_type,String jsession_id) throws Exception;
}

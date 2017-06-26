package com.tydic.unicom.uoc.service.activiti.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface RevokeProcessServDu {

	/**
	 * 任务撤销
	 * */
	public UocMessage revokeProcess(String proc_inst_id,String order_no) throws Exception;
}

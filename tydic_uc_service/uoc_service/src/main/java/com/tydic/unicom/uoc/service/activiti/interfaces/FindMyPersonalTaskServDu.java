package com.tydic.unicom.uoc.service.activiti.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface FindMyPersonalTaskServDu {

	/**
	 * 根据流程实例id查询当前任务
	 * */
	public UocMessage findMyPersonalTaskByInstanceId(String proc_inst_id,String order_no) throws Exception;
}

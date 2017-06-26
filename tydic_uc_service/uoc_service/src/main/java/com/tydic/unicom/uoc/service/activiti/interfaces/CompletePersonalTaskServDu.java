package com.tydic.unicom.uoc.service.activiti.interfaces;

import java.util.Map;
import com.tydic.unicom.webUtil.UocMessage;

public interface CompletePersonalTaskServDu {

	/**
	 * 提交任务
	 * */
	public UocMessage completePersonalTask(String proc_inst_id,String order_no,Map<String,String> actionCode) throws Exception;
	
	/**
	 * 提交流程
	 * */
	public UocMessage completeProcess(String proc_inst_id,String order_no) throws Exception;
}

package com.tydic.unicom.uoc.service.activiti.interfaces;

import java.util.Map;
import com.tydic.unicom.webUtil.UocMessage;

public interface CheckProcessServDu {

	/**
	 * 校验流程流转
	 * */
	public UocMessage checkProcess(String proc_inst_id,String order_no,String flow_type,Map<String,String> action_code) throws Exception;
}

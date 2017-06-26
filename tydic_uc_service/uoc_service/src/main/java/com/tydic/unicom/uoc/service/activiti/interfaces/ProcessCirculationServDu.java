package com.tydic.unicom.uoc.service.activiti.interfaces;

import java.util.Map;
import com.tydic.unicom.webUtil.UocMessage;

public interface ProcessCirculationServDu {

	/**
	 * 流程流转
	 * */
	public UocMessage processCirculation(String orderNo,String operType,String flowType,Map<String,String> actionCode,String jsonInfoExt) throws Exception;
}

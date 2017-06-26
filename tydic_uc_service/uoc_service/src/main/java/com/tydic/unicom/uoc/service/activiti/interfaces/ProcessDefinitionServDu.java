package com.tydic.unicom.uoc.service.activiti.interfaces;

import com.tydic.unicom.webUtil.UocMessage;

public interface ProcessDefinitionServDu {

	/**
	 * 获取流程定义信息
	 * */
	public UocMessage findProcessDefinition() throws Exception;
}

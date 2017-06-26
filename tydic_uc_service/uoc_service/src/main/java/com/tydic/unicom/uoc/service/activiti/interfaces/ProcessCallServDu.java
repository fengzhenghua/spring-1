package com.tydic.unicom.uoc.service.activiti.interfaces;

import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface ProcessCallServDu {

	/**
	 * 环节调用
	 * */
	public UocMessage processCall(String orderNo,String operType,String callType,String jsonInfoExt,ProcInstTaskInstVo procInstTaskInstVo) throws Exception;
}

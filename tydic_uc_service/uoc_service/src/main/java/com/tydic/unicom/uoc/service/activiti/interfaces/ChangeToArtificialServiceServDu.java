package com.tydic.unicom.uoc.service.activiti.interfaces;

import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface ChangeToArtificialServiceServDu {

	/**
	 * BASE0013 转人工任务服务
	 * */

	public UocMessage changeToArtificialService(ProcInstTaskInstVo procInstTaskInstVo,String task_property) throws Exception;

}

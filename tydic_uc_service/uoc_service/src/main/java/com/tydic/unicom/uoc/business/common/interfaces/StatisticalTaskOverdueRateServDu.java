package com.tydic.unicom.uoc.business.common.interfaces;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.webUtil.UocMessage;

public interface StatisticalTaskOverdueRateServDu {

	/**获取任务逾期率*/
	public UocMessage getStatisticalTaskOverdueRate(ParaVo paraVo) throws Exception;
}

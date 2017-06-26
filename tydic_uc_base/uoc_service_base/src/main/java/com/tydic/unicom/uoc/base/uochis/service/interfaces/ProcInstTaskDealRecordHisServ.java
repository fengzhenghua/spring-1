package com.tydic.unicom.uoc.base.uochis.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskDealRecordHisPo;

public interface ProcInstTaskDealRecordHisServ {
	/**
	 * 写入人工任务操作记录历史表
	 * @param po
	 * @return
	 * @throws Exception
	 */

	public boolean createProcInstTaskDealRecordHisPo(ProcInstTaskDealRecordHisPo po)throws Exception;

	List<ProcInstTaskDealRecordHisPo> queryTaskDealRecordHisByOrderNo(ProcInstTaskDealRecordHisPo po) throws Exception;

}

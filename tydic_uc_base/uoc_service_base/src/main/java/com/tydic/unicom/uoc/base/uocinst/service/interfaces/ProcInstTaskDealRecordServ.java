package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskDealRecordPo;

public interface ProcInstTaskDealRecordServ {
	
	/**
	 * 根据订单号查询人工任务操作记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<ProcInstTaskDealRecordPo> queryProcInstTaskDealRecordByOrderNo(ProcInstTaskDealRecordPo po)throws Exception;
	
	
	/**
	 * 根据订单号删除记录
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public boolean deleteProcInstTaskDealRecordByOrderNo(ProcInstTaskDealRecordPo po)throws Exception;
	
	public boolean create(ProcInstTaskDealRecordPo po)throws Exception;
	
	

}

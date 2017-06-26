package com.tydic.unicom.uoc.service.order.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.business.order.service.vo.ProcInstTaskDealRecordVo;

public interface ProcInstTaskDealRecordServDu {

	public List<ProcInstTaskDealRecordVo> queryProcInstTaskDealRecordByOrderNo(ProcInstTaskDealRecordVo vo)throws Exception;

	List<ProcInstTaskDealRecordVo> queryTaskDealRecordHisByOrderNo(ProcInstTaskDealRecordVo vo) throws Exception;
}

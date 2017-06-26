package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskAssignRecordPo;

public interface ProcInstTaskAssignRecordServ {
	
	public boolean create(ProcInstTaskAssignRecordPo procInstTaskAssignRecordPo) throws Exception;
	
	public List<ProcInstTaskAssignRecordPo> queryProcInstTaskAssignRecordByOrderNo(ProcInstTaskAssignRecordPo po) throws Exception;
	
	public boolean deleteProcTaskAssignRecordByOrderNo(ProcInstTaskAssignRecordPo po) throws Exception;
}

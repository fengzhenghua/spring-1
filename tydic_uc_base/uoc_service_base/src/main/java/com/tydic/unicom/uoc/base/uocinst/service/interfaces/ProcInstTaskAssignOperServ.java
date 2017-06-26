package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskAssignOperPo;

public interface ProcInstTaskAssignOperServ {

	public boolean create(ProcInstTaskAssignOperPo procInstTaskAssignOperPo) throws Exception;

	public boolean delete(ProcInstTaskAssignOperPo po) throws Exception;

	public boolean update(ProcInstTaskAssignOperPo po) throws Exception;

	public ProcInstTaskAssignOperPo queryProcInstTaskAssignOperByPo(ProcInstTaskAssignOperPo po) throws Exception;


}

package com.tydic.unicom.uoc.base.uocinst.service.interfaces;

import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskAssignDepartPo;

public interface ProcInstTaskAssignDepartServ {
	public boolean create(ProcInstTaskAssignDepartPo procInstTaskAssignDepartPo) throws Exception;

	public boolean delete(ProcInstTaskAssignDepartPo procInstTaskAssignDepartPo) throws Exception;

	public boolean update(ProcInstTaskAssignDepartPo procInstTaskAssignDepartPo) throws Exception;

	public ProcInstTaskAssignDepartPo queryProcInstTaskAssignDepartByPo(ProcInstTaskAssignDepartPo po) throws Exception;
}

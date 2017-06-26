package com.tydic.unicom.uoc.base.uochis.service.impl;


import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskAssignDepartHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskAssignDepartHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("ProcInstTaskAssignDepartHisServ")
public class ProcInstTaskAssignDepartHisServImpl extends BaseServImpl<ProcInstTaskAssignDepartHisPo> implements ProcInstTaskAssignDepartHisServ{

	@Override
	public boolean createProcInstTaskAssignDepartHisPo(
			ProcInstTaskAssignDepartHisPo po) throws Exception {
		if(po==null){
			return false;
		}
		create(ProcInstTaskAssignDepartHisPo.class,po);
		return true;
	}
	
	
}

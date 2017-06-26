package com.tydic.unicom.uoc.base.uochis.service.impl;

import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskAssignRecordHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskAssignRecordHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcInstTaskAssignRecordHisServ")
public class ProcInstTaskAssignRecordHisServImpl extends BaseServImpl<ProcInstTaskAssignRecordHisPo> implements ProcInstTaskAssignRecordHisServ{

	@Override
	public boolean createProcInstTaskAssignRecordHisPo(
			ProcInstTaskAssignRecordHisPo po) throws Exception {
		if(po==null){
			return false;
		}
		create(ProcInstTaskAssignRecordHisPo.class,po);
		return true;
	}
	
}

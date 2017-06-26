package com.tydic.unicom.uoc.base.uochis.service.impl;

import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskAssignOperHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskAssignOperHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcInstTaskAssignOperHisServ")
public class ProcInstTaskAssignOperHisServImpl extends BaseServImpl<ProcInstTaskAssignOperHisPo> implements ProcInstTaskAssignOperHisServ{

	@Override
	public boolean createProcInstTaskAssignOperHisPo(
			ProcInstTaskAssignOperHisPo po) throws Exception {
		if(po==null){
			return false;
		}
		create(ProcInstTaskAssignOperHisPo.class,po);
		return true;
	}

	
}

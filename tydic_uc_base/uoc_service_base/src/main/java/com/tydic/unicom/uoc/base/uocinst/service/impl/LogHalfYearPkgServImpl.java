package com.tydic.unicom.uoc.base.uocinst.service.impl;

import org.springframework.stereotype.Service;


import com.tydic.unicom.uoc.base.uocinst.po.LogHalfYearPkgPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.LogHalfYearPkgServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("LogHalfYearPkgServ")
public class LogHalfYearPkgServImpl extends BaseServImpl<LogHalfYearPkgPo> implements LogHalfYearPkgServ {

	@Override
	public boolean createLogHalfYearPkg(LogHalfYearPkgPo po)
			throws Exception {
		
		create(LogHalfYearPkgPo.class,po);
		
		return true;
	}

	
	
	
}

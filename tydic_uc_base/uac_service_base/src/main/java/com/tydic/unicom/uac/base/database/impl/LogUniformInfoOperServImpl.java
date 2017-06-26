package com.tydic.unicom.uac.base.database.impl;

import org.springframework.stereotype.Service;

import com.tydic.unicom.uac.base.database.interfaces.LogUniformInfoOperServ;
import com.tydic.unicom.uac.base.database.po.LogUniformInfoOperPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("LogUniformInfoOperServ")
public class LogUniformInfoOperServImpl extends BaseServImpl<LogUniformInfoOperPo> implements LogUniformInfoOperServ{

	@Override
	public boolean addLogUniformInfoOper(LogUniformInfoOperPo logUniformInfoOperPo) throws Exception {
		create(LogUniformInfoOperPo.class, logUniformInfoOperPo);
		return true;
	}

}

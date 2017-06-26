package com.tydic.unicom.uoc.base.uocinst.service.impl;

import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uocinst.po.InfoSmsWarnPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSmsWarnServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoSmsWarnServ")
public class InfoSmsWarnServImpl extends BaseServImpl<InfoSmsWarnPo> implements InfoSmsWarnServ{

	@Override
	public boolean createInfoSmsWarn(InfoSmsWarnPo po)throws Exception{
		create(InfoSmsWarnPo.class,po);
		return true;
	}

	@Override
	public boolean deleteInfoSmsWarn(InfoSmsWarnPo po) throws Exception {
		remove(InfoSmsWarnPo.class, po);
		return true;
	}

}

package com.tydic.unicom.uoc.base.uochis.service.impl;

import com.tydic.unicom.uoc.base.uochis.po.InfoSmsWarnHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSmsWarnHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

public class InfoSmsWarnHisServImpl extends BaseServImpl<InfoSmsWarnHisPo> implements InfoSmsWarnHisServ {

	@Override
	public boolean createInfoSmsWarnHis(InfoSmsWarnHisPo po) throws Exception {
		create(InfoSmsWarnHisPo.class, po);
		return true;
	}

}

package com.tydic.unicom.uac.base.database.impl;

import org.springframework.stereotype.Service;

import com.tydic.unicom.uac.base.database.interfaces.InfoInputOrderServ;
import com.tydic.unicom.uac.base.database.po.InfoInputOrderPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoInputOrderServ")
public class InfoInputOrderServImpl extends BaseServImpl<InfoInputOrderPo> implements InfoInputOrderServ {

	@Override
	public boolean createInfoInputOrder(InfoInputOrderPo po) {
		try {
			create(InfoInputOrderPo.class, po);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}

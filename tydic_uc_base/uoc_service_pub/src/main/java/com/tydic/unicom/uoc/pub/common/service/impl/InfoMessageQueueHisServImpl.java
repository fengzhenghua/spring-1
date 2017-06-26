package com.tydic.unicom.uoc.pub.common.service.impl;

import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.pub.common.service.interfaces.InfoMessageQueueHisServ;
import com.tydic.unicom.uoc.pub.common.service.po.InfoMessageQueueHisPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoMessageQueueHisServ")
public class InfoMessageQueueHisServImpl extends BaseServImpl<InfoMessageQueueHisPo> implements InfoMessageQueueHisServ {

	@Override
	public boolean createInfoMessageQueueHis(InfoMessageQueueHisPo po)
			throws Exception {
		try{
			create(InfoMessageQueueHisPo.class,po);	
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

}

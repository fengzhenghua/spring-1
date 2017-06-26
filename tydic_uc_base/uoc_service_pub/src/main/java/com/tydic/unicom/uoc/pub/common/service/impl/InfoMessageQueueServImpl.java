package com.tydic.unicom.uoc.pub.common.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.pub.common.service.interfaces.InfoMessageQueueServ;
import com.tydic.unicom.uoc.pub.common.service.po.InfoMessageQueuePo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoMessageQueueServ")
public class InfoMessageQueueServImpl extends BaseServImpl<InfoMessageQueuePo> implements InfoMessageQueueServ {

	@Override
	public boolean createInfoMessageQueue(InfoMessageQueuePo po)
			throws Exception {
		try{
			create(InfoMessageQueuePo.class,po);	
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public InfoMessageQueuePo getInfoMessageQueue(InfoMessageQueuePo po)
			throws Exception {	
		return get(InfoMessageQueuePo.class,po);
	}

	@Override
	public List<InfoMessageQueuePo> queryAllMessageQueue(InfoMessageQueuePo po) throws Exception {
		Condition condition = Condition.build("queryAllMessageQueue").filter(po.convertThis2Map());
		List<InfoMessageQueuePo> list =query(InfoMessageQueuePo.class,condition);
		return list;
	}

	@Override
	public boolean deleteInfoMessageQueueById(InfoMessageQueuePo po)
			throws Exception {
		try{	
			remove(InfoMessageQueuePo.class,po);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
			return true;
	}
	
}

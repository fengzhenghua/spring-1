package com.tydic.unicom.uoc.pub.common.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.pub.common.service.po.InfoMessageQueuePo;

public interface InfoMessageQueueServ {

	public boolean createInfoMessageQueue(InfoMessageQueuePo po) throws Exception;
	
	public InfoMessageQueuePo getInfoMessageQueue(InfoMessageQueuePo po) throws Exception;
	
	public List<InfoMessageQueuePo> queryAllMessageQueue(InfoMessageQueuePo po) throws Exception;
	
	public boolean deleteInfoMessageQueueById(InfoMessageQueuePo po) throws Exception;
}

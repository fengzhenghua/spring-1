package com.tydic.unicom.uoc.pub.common.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.pub.common.service.interfaces.ActivemqSendServ;
import com.tydic.unicom.uoc.pub.common.service.po.ActivemqSendPo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("ActivemqSendServ")
public class ActivemqSendServImpl implements ActivemqSendServ{

	Logger logger = Logger.getLogger(ActivemqSendServImpl.class);
	
	@Override
	public UocMessage SendMessage(ActivemqSendPo activemqSendPo,String queueName) throws Exception {
		UocMessage UocMessage = new UocMessage();
		int res = S.get(ActivemqSendPo.class).batch(Condition.empty().filter("destination", queueName), activemqSendPo);
		logger.info("===================发送消息队列结果================="+res);
		if(res==0){
			UocMessage.setRespCode(RespCodeContents.SUCCESS);
		}
		else{
			UocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			UocMessage.setContent("发送消息队列失败");
		}
		return UocMessage;
	}

}

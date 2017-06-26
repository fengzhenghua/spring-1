package com.tydic.unicom.uoc.service.common.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.service.common.interfaces.CrawlerActivemqSendServDu;
import com.tydic.unicom.webUtil.CrawlerActivemqSendPo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;
@Service("CrawlerActivemqSendServDu")
public class CrawlerActivemqSendServDuImpl implements CrawlerActivemqSendServDu{

	private static Logger logger = Logger.getLogger(CrawlerActivemqSendServDuImpl.class);
	
	@Override
	public UocMessage sendActivemqMessage(CrawlerActivemqSendPo crawlerActivemqSendPo, String queueName) throws Exception {
		UocMessage UocMessage = new UocMessage();
		int res = S.get(CrawlerActivemqSendPo.class).batch(Condition.empty().filter("destination", queueName), crawlerActivemqSendPo);
		if(res==0){
			logger.info("=============发送消息队列成功（爬虫）===========");
			UocMessage.setRespCode(RespCodeContents.SUCCESS);
			UocMessage.setContent("发送消息队列成功");
		}
		else{
			logger.info("=============发送消息队列失败（爬虫）===========");
			UocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			UocMessage.setContent("发送消息队列失败");
		}
		return UocMessage;
	}

}

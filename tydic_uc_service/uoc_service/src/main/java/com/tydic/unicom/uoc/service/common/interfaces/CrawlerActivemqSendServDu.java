package com.tydic.unicom.uoc.service.common.interfaces;

import com.tydic.unicom.webUtil.CrawlerActivemqSendPo;
import com.tydic.unicom.webUtil.UocMessage;

public interface CrawlerActivemqSendServDu {

	public UocMessage sendActivemqMessage(CrawlerActivemqSendPo crawlerActivemqSendPo,String queueName) throws Exception;
}

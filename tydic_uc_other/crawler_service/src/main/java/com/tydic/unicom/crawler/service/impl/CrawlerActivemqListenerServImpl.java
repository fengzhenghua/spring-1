package com.tydic.unicom.crawler.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tydic.unicom.crawler.business.interfaces.WriteBackServiceBus;
import com.tydic.unicom.crawler.business.vo.BusinessResParamVo;
import com.tydic.unicom.crawler.service.interfaces.CrawlerActivemqListenerServ;
import com.tydic.unicom.webUtil.CrawlerActivemqSendPo;

public class CrawlerActivemqListenerServImpl implements CrawlerActivemqListenerServ {

	private static Logger logger = Logger.getLogger(CrawlerActivemqListenerServImpl.class);

	@Autowired
	WriteBackServiceBus writeBackServiceBusImpl;

	@Override
	public void getMessageFromCrawler(Object message) throws Exception {
		CrawlerActivemqSendPo crawlerActivemqSendPo = (CrawlerActivemqSendPo) message;
		if (crawlerActivemqSendPo != null) {
			BusinessResParamVo businessResParamVo = new BusinessResParamVo();
			businessResParamVo.setJsonInfo(crawlerActivemqSendPo.getJson_info());
			try {
				writeBackServiceBusImpl.crawlerRecallMethod(businessResParamVo);
			} catch (Exception e) {
				// TODO: handle exception
				//写入文件中
				
			}
		}
		logger.info("==================消息队列接受的信息：" + crawlerActivemqSendPo.getJson_info());
	}

}

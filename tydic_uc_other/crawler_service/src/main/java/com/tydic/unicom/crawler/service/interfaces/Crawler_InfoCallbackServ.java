package com.tydic.unicom.crawler.service.interfaces;

import com.tydic.unicom.crawler.dao.po.Crawler_InfocallbackPo;

public interface Crawler_InfoCallbackServ extends BaseServ<Crawler_InfocallbackPo>{

	public boolean addCrawlerInfoCallBack(Crawler_InfocallbackPo crawler_InfocallbackPo) throws Exception;
}

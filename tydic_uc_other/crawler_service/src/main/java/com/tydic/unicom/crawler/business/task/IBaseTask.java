package com.tydic.unicom.crawler.business.task;

import com.tydic.unicom.crawlerframe.model.CrawlDatum;

public interface IBaseTask {
	public void exe(CrawlDatum datum);
}

package com.tydic.unicom.crawler.common;

import com.tydic.unicom.crawler.dao.po.Crawler_Mall_UserPo;
import com.tydic.unicom.crawlerframe.model.CrawlDatum;

public class BustoMallVo extends Crawler_Mall_UserPo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String safecode;
	public String getSafecode() {
		return safecode;
	}
	public void setSafecode(String safecode) {
		this.safecode = safecode;
	}
	
	
}

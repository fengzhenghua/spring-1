package com.tydic.unicom.crawler.dao.interfaces;

import com.tydic.unicom.crawler.dao.po.Crawler_InfocallbackPo;

public interface Crawler_InfocallbackDao extends BaseDao<Crawler_InfocallbackPo>{
	public Crawler_InfocallbackPo queryByCiuuidAndTachecode(Crawler_InfocallbackPo crawler_InfocallbackPo) throws Exception;
}

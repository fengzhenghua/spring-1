package com.tydic.unicom.crawler.dao.interfaces;



import java.util.HashMap;
import java.util.List;

import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;

public interface Crawler_InfoDao extends BaseDao<Crawler_InfoPo>{
	
	public Crawler_InfoPo queryCrawlerInfoByOrderNo(Crawler_InfoPo Crawler_InfoPo) throws Exception;
	public List<Crawler_InfoPo> execsql(Crawler_InfoPo Crawler_InfoPo) throws Exception;

	public List<Crawler_InfoPo> getCountStatus(Crawler_InfoPo Crawler_InfoPo) throws Exception;
}

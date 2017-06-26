package com.tydic.unicom.crawler.service.interfaces;

import java.util.List;

import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;

public interface Crawler_InfoServ extends BaseServ<Crawler_InfoPo>{

	public Crawler_InfoPo queryCrawlerInfoByOrderNo(Crawler_InfoPo Crawler_InfoPo) throws Exception;
	public List<Crawler_InfoPo> likequery(Crawler_InfoPo po) throws Exception;
	
	public List<Crawler_InfoPo> getCountStatus(Crawler_InfoPo Crawler_InfoPo) throws Exception;
}

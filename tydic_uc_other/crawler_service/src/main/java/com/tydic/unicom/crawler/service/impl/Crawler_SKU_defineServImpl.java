package com.tydic.unicom.crawler.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawler.dao.interfaces.Crawler_SKU_defineDao;
import com.tydic.unicom.crawler.dao.po.Crawler_SKU_definePo;
import com.tydic.unicom.crawler.service.interfaces.Crawler_SKU_defineServ;

@Service
public class Crawler_SKU_defineServImpl implements Crawler_SKU_defineServ {

	@Autowired
	Crawler_SKU_defineDao dao;
	
	@Override
	public boolean create(Crawler_SKU_definePo po) throws Exception {
		
		return dao.create(po);
	}

	@Override
	public boolean delete(Crawler_SKU_definePo po) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Crawler_SKU_definePo po) throws Exception {
		return dao.update(po);
	}

	@Override
	public Crawler_SKU_definePo get(Crawler_SKU_definePo po) throws Exception {
		
		return dao.get(po);
	}

	@Override
	public List<Crawler_SKU_definePo> query(Crawler_SKU_definePo po) throws Exception {
		try {
			List l = dao.query(po);
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}

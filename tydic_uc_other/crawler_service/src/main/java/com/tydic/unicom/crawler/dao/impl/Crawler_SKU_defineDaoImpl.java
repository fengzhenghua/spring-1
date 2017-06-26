package com.tydic.unicom.crawler.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.DataService;
import com.tydic.uda.service.S;
import com.tydic.unicom.crawler.dao.interfaces.Crawler_SKU_defineDao;
import com.tydic.unicom.crawler.dao.po.Crawler_SKU_definePo;

@Service
public class Crawler_SKU_defineDaoImpl implements Crawler_SKU_defineDao{
	
	private static Logger logger = Logger.getLogger(Crawler_SKU_defineDaoImpl.class);
	private static DataService<Crawler_SKU_definePo> s = S.get(Crawler_SKU_definePo.class);
	
	@Override
	public boolean create(Crawler_SKU_definePo po) throws Exception {
		s.create(po);
		return false;
	}

	@Override
	public boolean delete(Crawler_SKU_definePo po) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Crawler_SKU_definePo po) throws Exception {
		s.update(po);
		return false;
	}

	@Override
	public Crawler_SKU_definePo get(Crawler_SKU_definePo po) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Crawler_SKU_definePo> query(Crawler_SKU_definePo po) throws Exception {
		Condition condition = Condition.build("getskudefin").filter(po.convertToMap());
		List<Crawler_SKU_definePo> list = S.get(Crawler_SKU_definePo.class).query(condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;	
		}
	}
}

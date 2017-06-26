package com.tydic.unicom.crawler.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.DataService;
import com.tydic.uda.service.S;
import com.tydic.unicom.crawler.dao.interfaces.Crawler_InfocallbackDao;
import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;
import com.tydic.unicom.crawler.dao.po.Crawler_InfocallbackPo;

@Service
public class Crawler_InfocallbackDaoImpl implements Crawler_InfocallbackDao{
	private static Logger logger = Logger.getLogger(Crawler_InfocallbackDaoImpl.class);
	
	private static DataService<Crawler_InfocallbackPo> s = S.get(Crawler_InfocallbackPo.class);
	
	
	@Override
	public boolean create(Crawler_InfocallbackPo po) throws Exception {
		s.create(po);
		return true;
	}

	@Override
	public boolean delete(Crawler_InfocallbackPo po) throws Exception {
		return false;
	}

	@Override
	public boolean update(Crawler_InfocallbackPo po) throws Exception {
		
		return !(s.update(po)==0);
	}

	@Override
	public Crawler_InfocallbackPo get(Crawler_InfocallbackPo po) throws Exception {
		Crawler_InfocallbackPo repo = s.get(po);
		return repo;
	}

	@Override
	public List<Crawler_InfocallbackPo> query(Crawler_InfocallbackPo po) throws Exception {
		return null;
	}

	@Override
	public Crawler_InfocallbackPo queryByCiuuidAndTachecode(
			Crawler_InfocallbackPo crawler_InfocallbackPo) throws Exception {
		
//		HashMap map = new HashMap();
//		map.put("ci_uuid", crawler_InfocallbackPo.getCi_uuid());
//		map.put("tache_code", crawler_InfocallbackPo.getTache_code());
//		Condition condition = Condition.build("queryByCiuuidAndTachecode").filter(map);
		Condition condition = Condition.build("queryByCiuuidAndTachecode").filter(crawler_InfocallbackPo.convertToMap());
		List<Crawler_InfocallbackPo> list = s.query(condition);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;	
		}	
		}

}

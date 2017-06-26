package com.tydic.unicom.crawler.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.DataService;
import com.tydic.uda.service.S;
import com.tydic.unicom.crawler.dao.interfaces.Crawler_InfoDao;
import com.tydic.unicom.crawler.dao.po.Crawler_InfoPo;
import com.tydic.unicom.crawler.dao.po.Crawler_InfocallbackPo;


@Service
public class Crawler_InfoDaoImpl implements Crawler_InfoDao{
	
	private static Logger logger = Logger.getLogger(Crawler_InfoDaoImpl.class);
	private static DataService<Crawler_InfoPo> s = S.get(Crawler_InfoPo.class);
	@Override
	public boolean create(Crawler_InfoPo po) throws Exception {
		s.create(po);
//		s.create(null, infoAaleAttrHPo);
		return true;
	}

	@Override
	public boolean delete(Crawler_InfoPo po) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Crawler_InfoPo po) throws Exception {
		return !(s.update(po)==0);
	}

	@Override
	public Crawler_InfoPo get(Crawler_InfoPo po) throws Exception {
		return s.get(po);
	}

	@Override
	public List<Crawler_InfoPo> query(Crawler_InfoPo po) throws Exception {
		Map map = new HashMap();
		map.put("sql", po.getSql());
		Condition condition = Condition.build("querylistsql").filter(map);
		List<Crawler_InfoPo> list = S.get(Crawler_InfoPo.class).query(condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;	
		}
	}

	@Override
	public Crawler_InfoPo queryCrawlerInfoByOrderNo(
		Crawler_InfoPo crawler_infopo) throws Exception {

		Condition condition = Condition.build("queryCrawlerInfoByOrderNo").filter(crawler_infopo.convertToMap());
		List<Crawler_InfoPo> list = s.query(condition);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;	
		}
	}
	
	@Override
	public List<Crawler_InfoPo> execsql(Crawler_InfoPo po) throws Exception {
		Map map = new HashMap();
		map.put("sql", po.getSql());
		logger.debug(po.convertToMap());
		Condition condition = Condition.build("EXECSQL").filter(map);
		List<Crawler_InfoPo> list = S.get(Crawler_InfoPo.class).query(condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;	
		}
	}

	@Override
	public List<Crawler_InfoPo> getCountStatus(Crawler_InfoPo crawler_infopo) throws Exception {
		Condition condition = Condition.build("getCountStatus").filter(crawler_infopo.convertToMap());
		List<Crawler_InfoPo> list = s.query(condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;	
		}
	} 

}

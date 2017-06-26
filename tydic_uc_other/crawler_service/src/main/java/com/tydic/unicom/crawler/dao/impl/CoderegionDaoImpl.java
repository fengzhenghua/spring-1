package com.tydic.unicom.crawler.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.DataService;
import com.tydic.uda.service.S;
import com.tydic.unicom.crawler.dao.interfaces.CoderegionDao;
import com.tydic.unicom.crawler.dao.po.CoderegionPo;

@Service
public class CoderegionDaoImpl implements CoderegionDao{
	private static Logger logger = Logger.getLogger(CoderegionDaoImpl.class);
	private static DataService<CoderegionPo> s = S.get(CoderegionPo.class);
	@Override
	public boolean create(CoderegionPo po) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(CoderegionPo po) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(CoderegionPo po) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CoderegionPo get(CoderegionPo po) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CoderegionPo> query(CoderegionPo po) throws Exception {
		logger.info("=========query========"+po.getRegion_name());
		Map map = new HashMap();
		map.put("region_name", po.getRegion_name());
		Condition condition = Condition.build("getcoderegion").filter(map);
		List<CoderegionPo> list = S.get(CoderegionPo.class).query(condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;	
		}
	}

}

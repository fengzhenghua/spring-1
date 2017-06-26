package com.tydic.unicom.crawler.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.crawler.dao.interfaces.CoderegionDao;
import com.tydic.unicom.crawler.dao.po.CoderegionPo;
import com.tydic.unicom.crawler.service.interfaces.CoderegionServ;

/**
 * @author xx
 */
@Service
public class CoderegionServImpl implements CoderegionServ{
	@Autowired
	CoderegionDao dao;
	
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
		try {
			List l = dao.query(po);
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}

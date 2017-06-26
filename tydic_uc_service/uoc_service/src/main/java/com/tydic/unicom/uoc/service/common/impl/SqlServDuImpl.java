package com.tydic.unicom.uoc.service.common.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.common.interfaces.SqlServ;
import com.tydic.unicom.uoc.base.common.po.SqlPo;
import com.tydic.unicom.uoc.service.common.interfaces.SqlServDu;

@Service("SqlServDu")
public class SqlServDuImpl implements SqlServDu{

	Logger logger = Logger.getLogger(SqlServDuImpl.class);

	@Autowired
	private SqlServ sqlServ;

	@Override
	public boolean createBySql(String sql) throws Exception {
		SqlPo sqlPo = new SqlPo();
		sqlPo.setSql(sql);
		return sqlServ.createBySql(sqlPo);
	}

	@Override
	public boolean deleteBySql(String sql) throws Exception {
		SqlPo sqlPo = new SqlPo();
		sqlPo.setSql(sql);
		return sqlServ.deleteBySql(sqlPo);
	}

	@Override
	public boolean updateBySql(String sql) throws Exception {
		SqlPo sqlPo = new SqlPo();
		sqlPo.setSql(sql);
		return sqlServ.updateBySql(sqlPo);
	}
	
}

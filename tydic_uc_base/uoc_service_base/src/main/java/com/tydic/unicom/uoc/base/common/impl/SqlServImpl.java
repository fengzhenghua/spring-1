package com.tydic.unicom.uoc.base.common.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.common.interfaces.SqlServ;
import com.tydic.unicom.uoc.base.common.po.SqlPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("SqlServ")
public class SqlServImpl extends BaseServImpl<SqlPo> implements SqlServ{

	Logger logger = Logger.getLogger(SqlServImpl.class);
	
	@Override
	public boolean createBySql(SqlPo sqlPo) {
		logger.info("=====>>create sql <<=====:"+sqlPo.getSql());
		create(SqlPo.class,sqlPo);
		return true;
	}

	@Override
	public boolean updateBySql(SqlPo sqlPo) {
		logger.info("=====>>update sql <<=====:"+sqlPo.getSql());
		update(SqlPo.class,sqlPo);
		return true;
	}

	@Override
	public boolean deleteBySql(SqlPo sqlPo) {
		logger.info("=====>>delete sql <<=====:"+sqlPo.getSql());
		remove(SqlPo.class,sqlPo);
		return true;
	}

}

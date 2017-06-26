package com.tydic.unicom.uoc.base.common.interfaces;

import com.tydic.unicom.uoc.base.common.po.SqlPo;

public interface SqlServ {

	public boolean createBySql(SqlPo sqlPo);
	
	public boolean updateBySql(SqlPo sqlPo);
	
	public boolean deleteBySql(SqlPo sqlPo);
}

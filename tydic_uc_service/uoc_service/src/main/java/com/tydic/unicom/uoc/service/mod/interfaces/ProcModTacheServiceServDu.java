package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheServiceVo;

public interface ProcModTacheServiceServDu {

	public boolean create(ProcModTacheServiceVo procModTacheServiceVo)throws Exception;
	
	public boolean update(ProcModTacheServiceVo procModTacheServiceVo)throws Exception;
	
	public boolean delete(ProcModTacheServiceVo procModTacheServiceVo)throws Exception;

	
	public List<ProcModTacheServiceVo> queryProcModTacheServiceList(ProcModTacheServiceVo procModTacheServiceVo,int pageNo,int pageSize)throws Exception;
	
	public int queryProcModTacheServiceListCount(ProcModTacheServiceVo procModTacheServiceVo)throws Exception;
	
	
	/**
	 * 根据环节编码，地域和省份查询
	 * */
	public List<ProcModTacheServiceVo> queryProcModTacheServiceByTacheCodeAndProvinceAndAreaAndOperCodeFromRedis(ProcModTacheServiceVo procModTacheServiceVo) throws Exception;
	
	public List<ProcModTacheServiceVo> queryProcModTacheServiceAll() throws Exception;

}

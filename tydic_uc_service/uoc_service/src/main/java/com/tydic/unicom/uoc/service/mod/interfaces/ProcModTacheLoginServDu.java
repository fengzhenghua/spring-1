package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheLoginVo;

public interface ProcModTacheLoginServDu {

	public List<ProcModTacheLoginVo> queryProcModTacheLoginByTacheCode(ProcModTacheLoginVo procModTacheLoginVo)throws Exception;

	public boolean create(ProcModTacheLoginVo procModTacheLoginVo)throws Exception;
	
	public boolean update(ProcModTacheLoginVo procModTacheLoginVo)throws Exception;
	
	public boolean delete(ProcModTacheLoginVo procModTacheLoginVo)throws Exception;
	
	public List<ProcModTacheLoginVo> queryProcModTacheLoginList(ProcModTacheLoginVo procModTacheLoginVo,int pageNo,int pageSize)throws Exception;
	
	public int queryProcModTacheLoginListCount(ProcModTacheLoginVo procModTacheLoginVo)throws Exception;
	
	public List<ProcModTacheLoginVo> queryProcModTacheLoginAll() throws Exception;
	
	
}

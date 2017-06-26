package com.tydic.unicom.uoc.service.activiti.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.activiti.vo.ProcModAppVo;

public interface ProcModAppServDu {

	/**
	 * 根据省份、地域、业务查询流程应用表
	 * */
	public ProcModAppVo queryProcModAppByProvinceCodeAndAreaCodeAndOperCodeFromRedis(ProcModAppVo procModAppVo) throws Exception;

	public boolean create(ProcModAppVo procModAppVo) throws Exception;
	
	public boolean update(ProcModAppVo procModAppVo) throws Exception;
	
	public boolean delete(ProcModAppVo procModAppVo) throws Exception;
	
	public List<ProcModAppVo> queryProcModAppAll() throws Exception;
		
	public List<ProcModAppVo> queryProcModAppList (ProcModAppVo vo,int pageNo,int pageSize)throws Exception;
	
	public int queryProcModAppListCount (ProcModAppVo vo)throws Exception;
	
	public ProcModAppVo queryProcModAppByOperCode(ProcModAppVo procModAppVo) throws Exception;
	
}

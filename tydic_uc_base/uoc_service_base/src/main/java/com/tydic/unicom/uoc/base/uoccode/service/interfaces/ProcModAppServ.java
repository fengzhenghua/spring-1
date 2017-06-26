package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.ProcModAppPo;

public interface ProcModAppServ {

	/**
	 * 根据省份、地域、业务查询流程应用表
	 * */
	public ProcModAppPo queryProcModAppByProvinceCodeAndAreaCodeAndOperCode(ProcModAppPo procModAppPo) throws Exception;
	
	public boolean create(ProcModAppPo procModAppPo) throws Exception;
	
	public boolean update(ProcModAppPo procModAppPo) throws Exception;
	
	public boolean delete(ProcModAppPo procModAppPo) throws Exception;
	
	public List<ProcModAppPo> queryProcModAppList(ProcModAppPo procModAppPo,int pageNo,int pageSize)throws Exception;
	
	public int queryProcModAppListCount(ProcModAppPo procModAppPo)throws Exception;
	
	/**
	 * 查询ProcModApp全部数据
	 * */
	public List<ProcModAppPo> queryProcModAppAll() throws Exception;
}

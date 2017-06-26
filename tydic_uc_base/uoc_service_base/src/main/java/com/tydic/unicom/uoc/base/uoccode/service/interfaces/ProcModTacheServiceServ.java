package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.ProcModTacheServicePo;

public interface ProcModTacheServiceServ {

	/**
	 * 操作环节关联服务表
	 */
	public boolean create(ProcModTacheServicePo procModTacheServicePo) throws Exception;
	
	public boolean update(ProcModTacheServicePo procModTacheServicePo) throws Exception;
	
	public boolean delete(ProcModTacheServicePo procModTacheServicePo) throws Exception;
	

	public List<ProcModTacheServicePo> queryProcModTacheServiceList(ProcModTacheServicePo procModTacheServicePo,int pageNo,int pageSize) throws Exception;
	
	public int queryProcModTacheServiceListCount(ProcModTacheServicePo procModTacheServicePo) throws Exception;
	
	/**
	 * 根据环节编码，地域和省份查询
	 * */
	public List<ProcModTacheServicePo> queryProcModTacheServiceByTacheCodeAndProvinceAndArea(ProcModTacheServicePo procModTacheServicePo) throws Exception;
	
	/**
	 * 查询ProcModTacheService全部数据
	 * */
	public List<ProcModTacheServicePo> queryProcModTacheServiceAll() throws Exception;

}

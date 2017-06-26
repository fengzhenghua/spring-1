package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.ProcModTachePo;

public interface ProcModTacheServ {

	/**
	 * 操作环节配置表
	 * @param procModTachePo
	 * @return
	 */
	public ProcModTachePo queryProcModTacheByTacheCode(ProcModTachePo procModTachePo);
	
	public boolean create(ProcModTachePo procModTachePo)throws Exception;
	
	public boolean update(ProcModTachePo procModTachePo)throws Exception;
	
	public boolean delete(ProcModTachePo procModTachePo)throws Exception;
	
	public List<ProcModTachePo> queryProcModTacheList(ProcModTachePo procModTachePo,int pageNo,int pageSize)throws Exception;
	
	public int queryProcModTacheListCount(ProcModTachePo procModTachePo)throws Exception;
	
	/**
	 * 查询ProcModTache全部数据
	 * */
	public List<ProcModTachePo> queryProcModTacheAll() throws Exception;
}

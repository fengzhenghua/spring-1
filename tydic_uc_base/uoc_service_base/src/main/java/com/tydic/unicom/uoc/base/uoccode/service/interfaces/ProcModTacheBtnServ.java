package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.ProcModTacheBtnPo;

public interface ProcModTacheBtnServ {

	public boolean create(ProcModTacheBtnPo procModTacheBtnPo)throws Exception;
	
	public boolean update(ProcModTacheBtnPo procModTacheBtnPo)throws Exception;
	
	public boolean delete(ProcModTacheBtnPo procModTacheBtnPo)throws Exception;
	
	public List<ProcModTacheBtnPo> queryProcModTacheBtnList(ProcModTacheBtnPo procModTacheBtnPo,int pageNo,int pageSize)throws Exception;
	
	public int queryProcModTacheBtnListCount(ProcModTacheBtnPo procModTacheBtnPo)throws Exception;
	
	/**
	 * 查询ProcModTacheBtn全部数据
	 * */
	public List<ProcModTacheBtnPo> queryProcModTacheBtnAll() throws Exception;
}

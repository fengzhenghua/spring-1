package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.ProcModTacheBtnVo;

public interface ProcModTacheBtnServDu {

	public boolean create(ProcModTacheBtnVo ProcModTacheBtnVo)throws Exception;
	
	public boolean update(ProcModTacheBtnVo ProcModTacheBtnVo)throws Exception;
	
	public boolean delete(ProcModTacheBtnVo ProcModTacheBtnVo)throws Exception;
	
	public List<ProcModTacheBtnVo> queryProcModTacheBtnList(ProcModTacheBtnVo ProcModTacheBtnVo,int pageNo,int pageSize)throws Exception;
	
	public int queryProcModTacheBtnListCount(ProcModTacheBtnVo ProcModTacheBtnVo)throws Exception;
	
	public List<ProcModTacheBtnVo> queryProcModTacheBtnAll() throws Exception;
	
	
}

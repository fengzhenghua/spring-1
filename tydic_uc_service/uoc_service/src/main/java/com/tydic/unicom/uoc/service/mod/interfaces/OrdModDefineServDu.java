package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.OrdModDefineVo;

public interface OrdModDefineServDu {

	public boolean create(OrdModDefineVo ordModDefineVo)throws Exception;
	
	public boolean update(OrdModDefineVo ordModDefineVo)throws Exception;
	
	public boolean delete(OrdModDefineVo ordModDefineVo)throws Exception;
	
	public List<OrdModDefineVo> queryOrdModDefineList(OrdModDefineVo ordModDefineVo,int pageNo,int pageSize)throws Exception;
	
	
	public int queryOrdModDefineListCount(OrdModDefineVo ordModDefineVo)throws Exception;
	
	public List<OrdModDefineVo> queryOrdModDefineAll() throws Exception;
		
}

package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.OrdModAppVo;

public interface OrdModAppServDu {

	public boolean create(OrdModAppVo ordModAppVo)throws Exception;
	
	public boolean update(OrdModAppVo ordModAppVo)throws Exception;
	
	public boolean delete(OrdModAppVo ordModAppVo)throws Exception;
	
	public List<OrdModAppVo> queryOrdModAppList(OrdModAppVo ordModAppVo,int pageNo,int pageSize)throws Exception;
		
	public int queryOrdModAppListCount(OrdModAppVo ordModAppVo)throws Exception;
	
	public List<OrdModAppVo> queryOrdModAppAll() throws Exception;
}

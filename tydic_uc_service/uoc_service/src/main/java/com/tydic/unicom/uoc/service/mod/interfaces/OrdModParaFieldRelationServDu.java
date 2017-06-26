package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.OrdModParaFieldRelationVo;

public interface OrdModParaFieldRelationServDu {

	public boolean create(OrdModParaFieldRelationVo ordModParaFieldRelationVo) throws Exception;
	
	public boolean update(OrdModParaFieldRelationVo ordModParaFieldRelationVo) throws Exception;
	
	public boolean delete(OrdModParaFieldRelationVo ordModParaFieldRelationVo) throws Exception;
	
	public int queryOrdModParaFieldRelationListCount(OrdModParaFieldRelationVo ordModParaFieldRelationVo) throws Exception;
	
	public List<OrdModParaFieldRelationVo> queryOrdModParaFieldRelationList(OrdModParaFieldRelationVo ordModParaFieldRelationVo,int pageNo,int pageSize) throws Exception;
	
	public List<OrdModParaFieldRelationVo> queryOrdModParaFieldRelationAll() throws Exception;
	
	
}

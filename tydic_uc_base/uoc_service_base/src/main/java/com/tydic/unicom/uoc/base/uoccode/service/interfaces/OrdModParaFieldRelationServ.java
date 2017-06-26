package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModParaFieldRelationPo;

public interface OrdModParaFieldRelationServ {
	
	/**
	 * 查询订单参数与数据库表字段关系表
	 * 
	 * @param mod_code
	 * @return List
	 * @throws 
	 */
	public List<OrdModParaFieldRelationPo> queryOrdModParaFieldRelationByModeCode(OrdModParaFieldRelationPo ordModParaFieldRelationPo);

	public boolean create(OrdModParaFieldRelationPo ordModParaFieldRelationPo);
	
	public boolean update(OrdModParaFieldRelationPo ordModParaFieldRelationPo);
	
	public boolean delete(OrdModParaFieldRelationPo ordModParaFieldRelationPo);
	
	public List<OrdModParaFieldRelationPo> queryOrdModParaFieldRelationList(OrdModParaFieldRelationPo ordModParaFieldRelationPo,int pageNo,int pageSize)throws Exception;

	public int queryOrdModParaFieldRelationListCount(OrdModParaFieldRelationPo ordModParaFieldRelationPo)throws Exception;
	
	/**
	 * 查询OrdModParaFieldRelation全部数据
	 * */
	public List<OrdModParaFieldRelationPo> queryOrdModParaFieldRelationAll() throws Exception;
}

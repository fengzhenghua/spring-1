package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.OrdModTacheRuleVo;

public interface OrdModTacheRuleServDu {
	
	public boolean create(OrdModTacheRuleVo ordModTacheRuleVo) throws Exception;
	
	public boolean update(OrdModTacheRuleVo ordModTacheRuleVo)throws Exception;
	
	public boolean delete(OrdModTacheRuleVo ordModTacheRuleVo)throws Exception;
	
	/*
	 * 根据oper_code+tache_code查询环节订单状态关系表
	 */
	public OrdModTacheRuleVo queryOrdModTacheRuleByOperCodeAndTacheCode(OrdModTacheRuleVo vo) throws Exception;

	public List<OrdModTacheRuleVo> queryOrdModTacheRuleList(OrdModTacheRuleVo vo,int pageNo,int pageSize) throws Exception;

	/*
	 * 根据若干条件查询统计，分页
	 */
	public int queryOrdModTacheRuleListConut(OrdModTacheRuleVo vo) throws Exception;
	
	public List<OrdModTacheRuleVo> queryOrdModTacheRuleAll() throws Exception;
}

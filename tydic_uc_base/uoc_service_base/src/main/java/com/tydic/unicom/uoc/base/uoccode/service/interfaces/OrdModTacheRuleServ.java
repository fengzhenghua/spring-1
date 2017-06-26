package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModTacheRulePo;

public interface OrdModTacheRuleServ {
	
	/**
	 * 操作服务订单环节关系表
	 * @param ordModTacheRulePo
	 * @return
	 */
	public boolean create(OrdModTacheRulePo ordModTacheRulePo);
	
	public boolean update(OrdModTacheRulePo ordModTacheRulePo);
	
	public boolean delete(OrdModTacheRulePo ordModTacheRulePo);
	/*
	 * 根据oper_code+tache_code查询环节订单状态关系表
	 */
	public OrdModTacheRulePo queryOrdModTacheRuleByOperCodeAndTacheCode(OrdModTacheRulePo po) throws Exception;

	/*
	 * 根据若干条件查询，分页
	 */
	public List<OrdModTacheRulePo> queryOrdModTacheRuleList(OrdModTacheRulePo po,int pageNo,int pageSize) throws Exception;

	/*
	 * 根据若干条件查询统计，分页
	 */
	public int queryOrdModTacheRuleListConut(OrdModTacheRulePo po) throws Exception;
	
	/**
	 * 查询OrdModTacheRule全部数据
	 * */
	public List<OrdModTacheRulePo> queryOrdModTacheRuleAll() throws Exception;
}

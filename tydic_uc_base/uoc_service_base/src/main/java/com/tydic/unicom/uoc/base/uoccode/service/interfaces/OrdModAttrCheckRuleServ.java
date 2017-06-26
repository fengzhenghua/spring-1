package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModAttrCheckRulePo;

public interface OrdModAttrCheckRuleServ {

	/*
	 * 操作订单模板参数校验规则表
	 */
	public boolean create(OrdModAttrCheckRulePo ordModAttrCheckRulePo);
	
	public boolean update(OrdModAttrCheckRulePo ordModAttrCheckRulePo);
	
	public boolean delete(OrdModAttrCheckRulePo ordModAttrCheckRulePo);
	
	/*
	 * 根据若干条件查询,分页
	 */
	public List<OrdModAttrCheckRulePo> queryOrdModAttrCheckRuleList(OrdModAttrCheckRulePo po,int pageNo,int pageSize)throws Exception;
	
	public  int queryOrdModAttrCheckRuleListCount(OrdModAttrCheckRulePo po)throws Exception;
	
	/**
	 * 查询OrdModAttrCheckRule全部数据
	 * */
	public List<OrdModAttrCheckRulePo> queryOrdModAttrCheckRuleAll() throws Exception;
}

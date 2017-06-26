package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModCheckRulePo;

public interface OrdModCheckRuleServ {
	/**
	 * 操作订单模板校验规则表
	 * @param ordModCheckRulePo
	 * @return
	 */

	public boolean create(OrdModCheckRulePo ordModCheckRulePo);
	
	public boolean update(OrdModCheckRulePo ordModCheckRulePo);
	
	public boolean delete(OrdModCheckRulePo ordModCheckRulePo);
	
	public List<OrdModCheckRulePo> queryOrdModCheckRuleList(OrdModCheckRulePo ordModCheckRulePo,int pageNo,int pageSize)throws Exception;

	public int queryOrdModCheckRuleListCount(OrdModCheckRulePo ordModCheckRulePo)throws Exception;
	
	/**
	 * 查询OrdModCheckRule全部数据
	 * */
	public List<OrdModCheckRulePo> queryOrdModCheckRuleAll() throws Exception;

}

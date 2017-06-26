package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModStateRulePo;

public interface OrdModStateRuleServ {
	
	/**
	 * 操作服务订单状态关系表
	 * @param modOrderStateRule
	 * @return
	 */
	public boolean create(OrdModStateRulePo modOrderStateRule);
	
	public boolean delete(OrdModStateRulePo modOrderStateRule);
	
	public boolean update(OrdModStateRulePo modOrderStateRule);
	
	/*
	 * 根据若干条件查询，分页
	 */
	public List<OrdModStateRulePo> getModOrderStateRuleList(OrdModStateRulePo modOrderStateRule,int pageNo,int pageSize);
	
	/*
	 * 根据id...
	 */
	public OrdModStateRulePo getModOrderStateRuleById(OrdModStateRulePo modOrderStateRule);
	/*
	 * 根据oper_code+stat_code查询服务订单状态关系表
	 */
	public OrdModStateRulePo queryOrdModStateRuleByOperCodeAndStateCode(OrdModStateRulePo po) throws Exception;

	/*
	 * 分页，统计条数
	 */
	public int queryOrdModStateRuleCount(OrdModStateRulePo modOrderStateRule)throws Exception;
	
	/**
	 * 查询OrdModStateRule全部数据
	 * */
	public List<OrdModStateRulePo> queryOrdModStateRuleAll() throws Exception;
}

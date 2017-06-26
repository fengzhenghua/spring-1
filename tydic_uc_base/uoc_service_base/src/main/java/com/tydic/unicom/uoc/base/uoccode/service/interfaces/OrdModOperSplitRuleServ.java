package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.OrdModOperSplitRulePo;

public interface OrdModOperSplitRuleServ {
	
	public List<OrdModOperSplitRulePo> queryOrdModOperSplitRuleByOperCode(OrdModOperSplitRulePo po) throws Exception;
	
	/**
	 * 查询OrdModOperSplitRule全部数据
	 * */
	public List<OrdModOperSplitRulePo> queryOrdModOperSplitRuleAll() throws Exception;
}

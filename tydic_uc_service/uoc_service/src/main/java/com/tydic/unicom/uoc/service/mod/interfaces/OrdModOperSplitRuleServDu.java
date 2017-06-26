package com.tydic.unicom.uoc.service.mod.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.mod.vo.OrdModOperSplitRuleVo;

public interface OrdModOperSplitRuleServDu {

	public List<OrdModOperSplitRuleVo> queryOrdModOperSplitRuleByOperCode(OrdModOperSplitRuleVo vo) throws Exception;
	
	public List<OrdModOperSplitRuleVo> queryOrdModOperSplitRuleAll() throws Exception;

}

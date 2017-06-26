package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModOperSplitRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModOperSplitRuleServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("OrdModOperSplitRuleServ")
public class OrdModOperSplitRuleServImpl extends BaseServImpl<OrdModOperSplitRulePo> implements OrdModOperSplitRuleServ {

	@Override
	public List<OrdModOperSplitRulePo> queryOrdModOperSplitRuleByOperCode(OrdModOperSplitRulePo po)
			throws Exception {
		Condition condition = Condition.build("queryOrdModOperSplitRuleByOperCode").filter(po.convertThis2Map());
		List<OrdModOperSplitRulePo> list = query(OrdModOperSplitRulePo.class, condition);
		if(list!=null&&list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public List<OrdModOperSplitRulePo> queryOrdModOperSplitRuleAll() throws Exception {
		Condition condition = Condition.build("queryOrdModOperSplitRuleAll");
		List<OrdModOperSplitRulePo> list = query(OrdModOperSplitRulePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}

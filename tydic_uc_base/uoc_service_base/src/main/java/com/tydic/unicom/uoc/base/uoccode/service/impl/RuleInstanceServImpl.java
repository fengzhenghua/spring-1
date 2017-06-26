package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.RuleInstancePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.RuleInstanceServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("RuleInstanceServ")
public class RuleInstanceServImpl extends BaseServImpl<RuleInstancePo> implements RuleInstanceServ{

	@Override
	public List<RuleInstancePo> queryRuleInstanceAll() throws Exception {
		Condition condition = Condition.build("queryRuleInstanceAll");
		List<RuleInstancePo> list = query(RuleInstancePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}

package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.RuleDefinePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.RuleDefineServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("RuleDefineServ")
public class RuleDefineServImpl extends BaseServImpl<RuleDefinePo> implements RuleDefineServ{

	@Override
	public List<RuleDefinePo> queryRuleDefineAll() throws Exception {
		Condition condition = Condition.build("queryRuleDefineAll");
		List<RuleDefinePo> list = query(RuleDefinePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}

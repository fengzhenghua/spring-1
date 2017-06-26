package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.RuleTypePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.RuleTypeServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("RuleTypeServ")
public class RuleTypeServImpl extends BaseServImpl<RuleTypePo> implements RuleTypeServ{

	@Override
	public List<RuleTypePo> queryRuleTypeAll() throws Exception {
		Condition condition = Condition.build("queryRuleTypeAll");
		List<RuleTypePo> list = query(RuleTypePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}

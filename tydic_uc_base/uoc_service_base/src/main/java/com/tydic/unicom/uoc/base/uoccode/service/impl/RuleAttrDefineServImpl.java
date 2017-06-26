package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.RuleAttrDefinePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.RuleAttrDefineServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("RuleAttrDefineServ")
public class RuleAttrDefineServImpl extends BaseServImpl<RuleAttrDefinePo> implements RuleAttrDefineServ{

	@Override
	public List<RuleAttrDefinePo> queryRuleAttrDefineAll() throws Exception {
		Condition condition = Condition.build("queryRuleAttrDefineAll");
		List<RuleAttrDefinePo> list = query(RuleAttrDefinePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}

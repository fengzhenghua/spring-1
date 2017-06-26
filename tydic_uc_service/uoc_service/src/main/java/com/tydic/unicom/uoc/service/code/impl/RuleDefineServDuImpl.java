package com.tydic.unicom.uoc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.RuleDefinePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.RuleDefineServ;
import com.tydic.unicom.uoc.service.code.interfaces.RuleDefineServDu;
import com.tydic.unicom.uoc.service.code.vo.RuleDefineVo;

@Service("RuleDefineServDu")
public class RuleDefineServDuImpl implements RuleDefineServDu{

	@Autowired
	private RuleDefineServ ruleDefineServ;
	
	@Override
	public List<RuleDefineVo> queryRuleDefineAll() throws Exception {
		List<RuleDefinePo> list = ruleDefineServ.queryRuleDefineAll();
		if(list != null && list.size()>0){
			List<RuleDefineVo> listOut = new ArrayList<RuleDefineVo>();
			for(int i=0;i<list.size();i++){
				RuleDefineVo ruleDefineVo = new RuleDefineVo();
				BeanUtils.copyProperties(list.get(i), ruleDefineVo);
				listOut.add(ruleDefineVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}

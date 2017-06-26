package com.tydic.unicom.uoc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.RuleAttrDefinePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.RuleAttrDefineServ;
import com.tydic.unicom.uoc.service.code.interfaces.RuleAttrDefineServDu;
import com.tydic.unicom.uoc.service.code.vo.RuleAttrDefineVo;

@Service("RuleAttrDefineServDu")
public class RuleAttrDefineServDuImpl implements RuleAttrDefineServDu{

	@Autowired
	private RuleAttrDefineServ ruleAttrDefineServ;
	
	@Override
	public List<RuleAttrDefineVo> queryRuleAttrDefineAll() throws Exception {
		List<RuleAttrDefinePo> list = ruleAttrDefineServ.queryRuleAttrDefineAll();
		if(list != null && list.size()>0){
			List<RuleAttrDefineVo> listOut = new ArrayList<RuleAttrDefineVo>();
			for(int i=0;i<list.size();i++){
				RuleAttrDefineVo ruleAttrDefineVo = new RuleAttrDefineVo();
				BeanUtils.copyProperties(list.get(i), ruleAttrDefineVo);
				listOut.add(ruleAttrDefineVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}

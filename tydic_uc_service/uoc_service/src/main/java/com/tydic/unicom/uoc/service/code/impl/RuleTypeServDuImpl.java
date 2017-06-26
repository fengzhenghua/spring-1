package com.tydic.unicom.uoc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.RuleTypePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.RuleTypeServ;
import com.tydic.unicom.uoc.service.code.interfaces.RuleTypeServDu;
import com.tydic.unicom.uoc.service.code.vo.RuleTypeVo;

@Service("RuleTypeServDu")
public class RuleTypeServDuImpl implements RuleTypeServDu{

	@Autowired
	private RuleTypeServ ruleTypeServ;
	
	@Override
	public List<RuleTypeVo> queryRuleTypeAll() throws Exception {
		List<RuleTypePo> list = ruleTypeServ.queryRuleTypeAll();
		if(list != null && list.size()>0){
			List<RuleTypeVo> listOut = new ArrayList<RuleTypeVo>();
			for(int i=0;i<list.size();i++){
				RuleTypeVo ruleTypeVo = new RuleTypeVo();
				BeanUtils.copyProperties(list.get(i), ruleTypeVo);
				listOut.add(ruleTypeVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}

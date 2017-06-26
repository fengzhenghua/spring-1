package com.tydic.unicom.uoc.service.code.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.RuleInstancePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.RuleInstanceServ;
import com.tydic.unicom.uoc.service.code.interfaces.RuleInstanceServDu;
import com.tydic.unicom.uoc.service.code.vo.RuleInstanceVo;

@Service("RuleInstanceServDu")
public class RuleInstanceServDuImpl implements RuleInstanceServDu{

	@Autowired
	private RuleInstanceServ ruleInstanceServ;
	
	@Override
	public List<RuleInstanceVo> queryRuleInstanceAll() throws Exception {
		List<RuleInstancePo> list = ruleInstanceServ.queryRuleInstanceAll();
		if(list != null && list.size()>0){
			List<RuleInstanceVo> listOut =  new ArrayList<RuleInstanceVo>();
			for(int i=0;i<list.size();i++){
				RuleInstanceVo ruleInstanceVo = new RuleInstanceVo();
				BeanUtils.copyProperties(list.get(i), ruleInstanceVo);
				listOut.add(ruleInstanceVo);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}

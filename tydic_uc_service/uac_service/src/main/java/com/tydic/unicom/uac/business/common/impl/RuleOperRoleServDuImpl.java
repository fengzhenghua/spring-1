package com.tydic.unicom.uac.business.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uac.base.database.interfaces.RuleOperRoleServ;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.base.database.po.RuleOperRolePo;
import com.tydic.unicom.uac.business.common.interfaces.RuleOperRoleServDu;
import com.tydic.unicom.uac.business.common.vo.InfoOperVo;
import com.tydic.unicom.uac.business.common.vo.RuleOperRoleVo;

public class RuleOperRoleServDuImpl implements RuleOperRoleServDu{

	@Autowired
	private RuleOperRoleServ ruleOperRoleServ;
	
	@Override
	public List<RuleOperRoleVo> queryRuleOperRoleByInfoOper(InfoOperVo infoOperVo) throws Exception {
		InfoOperPo infoOperPo=new InfoOperPo();
		BeanUtils.copyProperties(infoOperVo, infoOperPo);
		List<RuleOperRolePo> list = ruleOperRoleServ.queryRuleOperRoleByInfoOper(infoOperPo);
		if(list != null && list.size()>0){
			List<RuleOperRoleVo> listOut = new ArrayList<RuleOperRoleVo>();
			for(int i=0;i<list.size();i++){
				RuleOperRoleVo rRuleOperRoleVoTemp = new RuleOperRoleVo();
				BeanUtils.copyProperties(list.get(i), rRuleOperRoleVoTemp);
				listOut.add(rRuleOperRoleVoTemp);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean createRuleOperRole(RuleOperRoleVo vo)  throws Exception{
		RuleOperRolePo ruleOperRolePo =new RuleOperRolePo();
		BeanUtils.copyProperties(vo, ruleOperRolePo);
		return ruleOperRoleServ.createRuleOperRole(ruleOperRolePo);
	}

	@Override
	public boolean deleteRuleOperRole(RuleOperRoleVo vo)  throws Exception{
		RuleOperRolePo ruleOperRolePo =new RuleOperRolePo();
		BeanUtils.copyProperties(vo, ruleOperRolePo);
		return ruleOperRoleServ.deleteRuleOperRole(ruleOperRolePo);
	}

	@Override
	public boolean updateRuleOperRole(RuleOperRoleVo vo)  throws Exception{
		RuleOperRolePo ruleOperRolePo =new RuleOperRolePo();
		BeanUtils.copyProperties(vo, ruleOperRolePo);
		return ruleOperRoleServ.updateRuleOperRole(ruleOperRolePo);
	}

	@Override
	public List<RuleOperRoleVo> queryRuleOperRoleByOperNoOrRoleId(
			RuleOperRoleVo ruleOperRoleVo) throws Exception{
		RuleOperRolePo RuleOperRole=new RuleOperRolePo();
		BeanUtils.copyProperties(ruleOperRoleVo, RuleOperRole);
		List<RuleOperRolePo> list = ruleOperRoleServ.queryRuleOperRoleByOperNoOrRoleId(RuleOperRole);
		if(list != null && list.size()>0){
			List<RuleOperRoleVo> listOut = new ArrayList<RuleOperRoleVo>();
			for(int i=0;i<list.size();i++){
				RuleOperRoleVo rRuleOperRoleVoTemp = new RuleOperRoleVo();
				BeanUtils.copyProperties(list.get(i), rRuleOperRoleVoTemp);
				listOut.add(rRuleOperRoleVoTemp);
			}
			return listOut;
		}else{
			return null;
		}
	}

}

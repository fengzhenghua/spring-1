package com.tydic.unicom.uac.base.database.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uac.base.database.interfaces.RuleOperRoleServ;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.base.database.po.RuleOperRolePo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("RuleOperRoleServ")
public class RuleOperRoleServImpl extends BaseServImpl<RuleOperRolePo> implements RuleOperRoleServ{
	
	@Override
	public List<RuleOperRolePo> queryRuleOperRoleNameByInfoOper(InfoOperPo infoOperPo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oper_id", infoOperPo.getOper_id());
		if(infoOperPo.getUni_oper_id()==null||infoOperPo.getUni_oper_id().equals("")){
			infoOperPo.setUni_oper_id("DefaultUniOper");
		}
		map.put("uni_oper_id", infoOperPo.getUni_oper_id());
		Condition con = Condition.build("queryRuleOperRoleNameByOperId").filter(map);
		List<RuleOperRolePo> list = query(RuleOperRolePo.class, con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}
	
	@Override
	public List<RuleOperRolePo> queryRuleOperRoleByInfoOper(InfoOperPo infoOperPo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oper_id", infoOperPo.getOper_id());
		if(infoOperPo.getUni_oper_id()==null||infoOperPo.getUni_oper_id().equals("")){
			infoOperPo.setUni_oper_id("DefaultUniOper");
		}
		map.put("uni_oper_id", infoOperPo.getUni_oper_id());
		Condition con = Condition.build("queryRuleOperRoleByOperId").filter(map);
		List<RuleOperRolePo> list = query(RuleOperRolePo.class, con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public boolean createRuleOperRole(RuleOperRolePo po) {
		try{
			create(RuleOperRolePo.class,po);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteRuleOperRole(RuleOperRolePo po) {
	
		try{
			remove(RuleOperRolePo.class,po);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateRuleOperRole(RuleOperRolePo po) {
		
		try{
			update(RuleOperRolePo.class,po);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<RuleOperRolePo> queryRuleOperRoleByOperNoOrRoleId(
			RuleOperRolePo po) {
		Condition con = Condition.build("queryRuleOperRoleByOperNoOrRoleId").filter(po.convertThis2Map());
		List<RuleOperRolePo> list = query(RuleOperRolePo.class, con);
		return list;
	}

	@Override
	public List<RuleOperRolePo> queryRuleOperRoleByCreateOperatorId(String createOperatorId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("createOperatorId", createOperatorId);
		Condition con = Condition.build("queryRuleOperRoleByCreateOperatorId").filter(param);
		List<RuleOperRolePo> list = query(RuleOperRolePo.class, con);
		return list;
	}

}

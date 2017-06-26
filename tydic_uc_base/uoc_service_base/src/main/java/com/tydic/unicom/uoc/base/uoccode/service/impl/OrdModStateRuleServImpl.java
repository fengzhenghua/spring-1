package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModStateRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModStateRuleServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("OrdModStateRuleServ")
public class OrdModStateRuleServImpl extends BaseServImpl<OrdModStateRulePo> implements OrdModStateRuleServ{

	@Override
	public boolean create(OrdModStateRulePo modOrderStateRule) {
	    create(OrdModStateRulePo.class,modOrderStateRule);
		return true;
	}

	@Override
	public boolean delete(OrdModStateRulePo modOrderStateRule) {
		remove(OrdModStateRulePo.class,modOrderStateRule);
		return true;
	}

	@Override
	public boolean update(OrdModStateRulePo modOrderStateRule) {
		update(OrdModStateRulePo.class,modOrderStateRule);
		return true;
	}
	@Override
	public List<OrdModStateRulePo> getModOrderStateRuleList(
			OrdModStateRulePo modOrderStateRule,int pageNo,int pageSize) {
		
		int number = (pageNo-1)* pageSize;
		List<OrdModStateRulePo> list = S.get(OrdModStateRulePo.class).page(Condition.build("getModOrderStateRuleList").filter(modOrderStateRule.convertThis2Map()),number,pageSize);
		if(list == null || list.size() <= 0){
			return null;
		}
	    return list;
	}

	@Override
	public OrdModStateRulePo getModOrderStateRuleById(
			OrdModStateRulePo modOrderStateRule) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", modOrderStateRule.getId());
		Condition con = Condition.build("queryOrdModStateRuleById").filter(map);
		List<OrdModStateRulePo> list = query(OrdModStateRulePo.class,con);
		return (list==null||list.isEmpty())?null:list.get(0);
	}

	@Override
	public OrdModStateRulePo queryOrdModStateRuleByOperCodeAndStateCode(
			OrdModStateRulePo po) throws Exception {
				
		return get(OrdModStateRulePo.class,po);
	}

	@Override
	public int queryOrdModStateRuleCount(OrdModStateRulePo modOrderStateRule)
			throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", modOrderStateRule.getId());
		Condition con = Condition.build("queryOrdModStateRuleCount").filter(map);
		List<OrdModStateRulePo> res= query(OrdModStateRulePo.class,con);		
		return res.size();
	}

	@Override
	public List<OrdModStateRulePo> queryOrdModStateRuleAll() throws Exception {
		Condition condition = Condition.build("queryOrdModStateRuleAll");
		List<OrdModStateRulePo> list = query(OrdModStateRulePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}
	
	
}

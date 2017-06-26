package com.tydic.unicom.uoc.base.uoccode.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModTacheRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModTacheRuleServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("OrdModTacheRuleServ")
public class OrdModTacheRuleServImpl extends BaseServImpl<OrdModTacheRulePo> implements OrdModTacheRuleServ{

		
	@Override
	public boolean create(OrdModTacheRulePo ordModTacheRulePo) {
		create(OrdModTacheRulePo.class,ordModTacheRulePo);
		return true;
	}

	@Override
	public boolean update(OrdModTacheRulePo ordModTacheRulePo) {
		update(OrdModTacheRulePo.class,ordModTacheRulePo);
		return true;
	}

	@Override
	public boolean delete(OrdModTacheRulePo ordModTacheRulePo) {
		remove(OrdModTacheRulePo.class,ordModTacheRulePo);
		return true;
	}

	@Override
	public OrdModTacheRulePo queryOrdModTacheRuleByOperCodeAndTacheCode(
			OrdModTacheRulePo po) throws Exception {
		
		return get(OrdModTacheRulePo.class,po);
	}

	@Override
	public List<OrdModTacheRulePo> queryOrdModTacheRuleList(OrdModTacheRulePo po,int pageNo,int pageSize)
			throws Exception {
//		Map<String,Object> map =new HashMap<String,Object>();
//		map.put("province_code", po.getProvince_code());
//		map.put("oper_code_a", po.getOper_code_a());		
		Condition con = Condition.build("queryOrdModTacheRuleList").filter(po.convertThis2Map());
		int number = (pageNo-1)* pageSize;
		List<OrdModTacheRulePo> list = S.get(OrdModTacheRulePo.class).page(con, number, pageSize);
		if(list == null || list.size() <= 0){
			return null;
		}
	    return list;
	}

	@Override
	public int queryOrdModTacheRuleListConut(
			OrdModTacheRulePo po) throws Exception {
		Condition con = Condition.build("queryOrdModTacheRuleListConut").filter(po.convertThis2Map());		
		List<OrdModTacheRulePo> list = query(OrdModTacheRulePo.class,con);
		return list.size();
	}

	@Override
	public List<OrdModTacheRulePo> queryOrdModTacheRuleAll() throws Exception {
		Condition condition = Condition.build("queryOrdModTacheRuleAll");
		List<OrdModTacheRulePo> list = query(OrdModTacheRulePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}

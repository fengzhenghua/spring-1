package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModCheckRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModCheckRuleServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("OrdModCheckRuleServ")
public class OrdModCheckRuleServImpl extends BaseServImpl<OrdModCheckRulePo> implements OrdModCheckRuleServ{

	@Override
	public boolean create(OrdModCheckRulePo ordModCheckRulePo) {
		create(OrdModCheckRulePo. class,ordModCheckRulePo );
		return true ;

	}

	@Override
	public boolean update(OrdModCheckRulePo ordModCheckRulePo) {
		update(OrdModCheckRulePo. class,ordModCheckRulePo );
		return true ;

	}

	@Override
	public boolean delete(OrdModCheckRulePo ordModCheckRulePo) {
		remove(OrdModCheckRulePo. class,ordModCheckRulePo );
		return true;

	}

	@Override
	public List<OrdModCheckRulePo> queryOrdModCheckRuleList(
			OrdModCheckRulePo ordModCheckRulePo, int pageNo, int pageSize)
					throws Exception {
		Condition con = Condition. build("queryOrdModCheckRuleList" ).filter( ordModCheckRulePo.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<OrdModCheckRulePo> list = S.get(OrdModCheckRulePo. class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;

	}

	@Override
	public int queryOrdModCheckRuleListCount(OrdModCheckRulePo ordModCheckRulePo)
			throws Exception {
		Condition con = Condition. build("queryOrdModCheckRuleListCount" ).filter( ordModCheckRulePo.convertThis2Map());
		List<OrdModCheckRulePo> list=query(OrdModCheckRulePo. class,con );
		return list .size();

	}

	@Override
	public List<OrdModCheckRulePo> queryOrdModCheckRuleAll() throws Exception {
		Condition condition = Condition.build("queryOrdModCheckRuleAll");
		List<OrdModCheckRulePo> list = query(OrdModCheckRulePo. class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}



}

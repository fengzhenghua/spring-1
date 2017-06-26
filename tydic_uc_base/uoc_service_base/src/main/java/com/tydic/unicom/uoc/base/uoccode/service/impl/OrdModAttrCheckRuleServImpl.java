package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModAttrCheckRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModAttrCheckRuleServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("OrdModAttrCheckRuleServ")
public class OrdModAttrCheckRuleServImpl extends BaseServImpl<OrdModAttrCheckRulePo> implements OrdModAttrCheckRuleServ{

	@Override
	public boolean create(OrdModAttrCheckRulePo ordModAttrCheckRulePo) {
		create(OrdModAttrCheckRulePo.class, ordModAttrCheckRulePo);
		return true;
	}

	@Override
	public boolean update(OrdModAttrCheckRulePo ordModAttrCheckRulePo) {
		update(OrdModAttrCheckRulePo.class, ordModAttrCheckRulePo);
		return true;
	}

	@Override
	public boolean delete(OrdModAttrCheckRulePo ordModAttrCheckRulePo) {
		remove(OrdModAttrCheckRulePo.class, ordModAttrCheckRulePo);
		return true;
	}

	@Override
	public List<OrdModAttrCheckRulePo> queryOrdModAttrCheckRuleList(
			OrdModAttrCheckRulePo po,int pageNo,int pageSize) throws Exception {
		Condition con = Condition.build("queryOrdModAttrCheckRuleList").filter(po.convertThis2Map());
		int number = (pageNo -1)* pageSize ;
		List<OrdModAttrCheckRulePo> list = S.get(OrdModAttrCheckRulePo. class).page(con ,number ,pageSize );
		if(list == null || list.size() <= 0){
			return null ;
		}
		return list ;

	}

	@Override
	public int queryOrdModAttrCheckRuleListCount(OrdModAttrCheckRulePo po)
			throws Exception {
		Condition con = Condition. build("queryOrdModAttrCheckRuleListCount" ).filter( po.convertThis2Map());
		List<OrdModAttrCheckRulePo> list=query(OrdModAttrCheckRulePo.class,con );
		return list .size();
	}

	@Override
	public List<OrdModAttrCheckRulePo> queryOrdModAttrCheckRuleAll() throws Exception {
		Condition condition = Condition.build("queryOrdModAttrCheckRuleAll");
		List<OrdModAttrCheckRulePo> list = query(OrdModAttrCheckRulePo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}


}

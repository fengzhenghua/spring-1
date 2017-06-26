package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uoc.base.uoccode.po.ProcTaskAssignRulePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcTaskAssignRuleServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcTaskAssignRuleServ")
public class ProcTaskAssignRuleServImpl extends BaseServImpl<ProcTaskAssignRulePo> implements ProcTaskAssignRuleServ{

	@Override
	public ProcTaskAssignRulePo queryByProcTaskAssignRulePo(ProcTaskAssignRulePo po)
			throws Exception {
		Condition con = Condition.build("queryByProcInstTaskInst").filter(po.convertThis2Map());
		List<ProcTaskAssignRulePo> list= query(ProcTaskAssignRulePo.class,con);
		return (list==null||list.isEmpty())?null:list.get(0);
	}

	@Override
	public boolean create(ProcTaskAssignRulePo po) throws Exception {
		create(ProcTaskAssignRulePo.class,po);
		return true;
	}

	@Override
	public boolean delete(ProcTaskAssignRulePo po) throws Exception {
		remove(ProcTaskAssignRulePo.class, po);
		return true;
	}

	@Override
	public boolean update(ProcTaskAssignRulePo po) throws Exception {
		update(ProcTaskAssignRulePo.class,po);
		return true;
	}

	@Override
	public List<ProcTaskAssignRulePo> queryProcTaskAssignRuleByPage(ProcTaskAssignRulePo po,int pageNo, int pageSize) throws Exception {
		int number = (pageNo-1)* pageSize;
		List<ProcTaskAssignRulePo> list = S.get(ProcTaskAssignRulePo.class).page(Condition.build("queryProcTaskAssignRuleByPage").filter(po.convertThis2Map()), number,pageSize);
		if(list == null || list.size() <= 0){
			return null;
		}
		return list;
	}

	@Override
	public ProcTaskAssignRulePo queryProcTaskAssignRuleById(
			ProcTaskAssignRulePo po) throws Exception {
		Condition con = Condition.build("queryProcTaskAssignRuleById").filter(po.convertThis2Map());
		List<ProcTaskAssignRulePo> list= query(ProcTaskAssignRulePo.class,con);
		return (list==null||list.isEmpty())?null:list.get(0);
	}

	@Override
	public int queryProcTaskAssignRuleCount(ProcTaskAssignRulePo po)
			throws Exception {
		Condition con = Condition.build("queryProcTaskAssignRuleCount").filter(po.convertThis2Map());
		List<ProcTaskAssignRulePo> list = query(ProcTaskAssignRulePo.class, con);
		return list.size();
	}

}

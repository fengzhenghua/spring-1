package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.ProcTaskRuleSpecPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcTaskRuleSpecServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcTaskRuleSpecServ")
public class ProcTaskRuleSpecServImpl extends BaseServImpl<ProcTaskRuleSpecPo> implements ProcTaskRuleSpecServ {

	@Override
	public List<ProcTaskRuleSpecPo> queryProcTaskRuleSpecByPo(ProcTaskRuleSpecPo po) throws Exception {
		Condition con = Condition.build("queryTaskRuleSpecByPo").filter(po.convertThis2Map());
		List<ProcTaskRuleSpecPo> list = query(ProcTaskRuleSpecPo.class, con);
		return (list == null || list.isEmpty()) ? null : list;
	}

	@Override
	public boolean create(ProcTaskRuleSpecPo po) throws Exception {
		create(ProcTaskRuleSpecPo.class,po);
		return true;
	}

	@Override
	public boolean delete(ProcTaskRuleSpecPo po) throws Exception {
		remove(ProcTaskRuleSpecPo.class, po);
		return true;
	}

	@Override
	public boolean update(ProcTaskRuleSpecPo po) throws Exception {
		update(ProcTaskRuleSpecPo.class,po);
		return true;
	}

	@Override
	public ProcTaskRuleSpecPo queryProcTaskRuleSpecById(ProcTaskRuleSpecPo po)
			throws Exception {
		Condition con = Condition.build("queryProcTaskRuleSpecById").filter(po.convertThis2Map());
		List<ProcTaskRuleSpecPo> list= query(ProcTaskRuleSpecPo.class,con);
		return (list==null||list.isEmpty())?null:list.get(0);
	}

	@Override
	public List<ProcTaskRuleSpecPo> queryProcTaskRuleSpecByWeb(ProcTaskRuleSpecPo po) throws Exception {
		Condition con = Condition.build("queryProcTaskRuleSpecByWeb").filter(po.convertThis2Map());
		List<ProcTaskRuleSpecPo> list = query(ProcTaskRuleSpecPo.class, con);
		return list;
	}

	@Override
	public int queryProcTaskRuleSpecCount(ProcTaskRuleSpecPo po)
			throws Exception {
		Condition con = Condition.build("queryProcTaskRuleSpecCount").filter(po.convertThis2Map());
		List<ProcTaskRuleSpecPo> list = query(ProcTaskRuleSpecPo.class, con);
		return list.size();
	}

	@Override
	public List<ProcTaskRuleSpecPo> queryTaskRuleSpecGroupByRuleId(ProcTaskRuleSpecPo po) throws Exception {
		Condition con = Condition.build("queryTaskRuleSpecGroupByRuleId").filter(po.convertThis2Map());
		List<ProcTaskRuleSpecPo> list = query(ProcTaskRuleSpecPo.class, con);
		return (list == null || list.isEmpty()) ? null : list;
	}

}

package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.ProcTaskRuleDepartPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcTaskRuleDepartServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcTaskRuleDepartServ")
public class ProcTaskRuleDepartServImpl extends BaseServImpl<ProcTaskRuleDepartPo> implements ProcTaskRuleDepartServ {

	@Override
	public List<ProcTaskRuleDepartPo> queryProcTaskRuleDepartByPo(ProcTaskRuleDepartPo po) throws Exception {
		Condition con = Condition.build("queryTaskRuleDepartByPo").filter(po.convertThis2Map());
		List<ProcTaskRuleDepartPo> list = query(ProcTaskRuleDepartPo.class, con);
		return (list == null || list.isEmpty()) ? null : list;
	}

	@Override
	public boolean create(ProcTaskRuleDepartPo po) throws Exception {
		create(ProcTaskRuleDepartPo.class, po);
		return true;
	}

	@Override
	public boolean delete(ProcTaskRuleDepartPo po) throws Exception {
		remove(ProcTaskRuleDepartPo.class, po);
		return true;
	}

	@Override
	public boolean update(ProcTaskRuleDepartPo po) throws Exception {
		update(ProcTaskRuleDepartPo.class, po);
		return true;
	}

	@Override
	public List<ProcTaskRuleDepartPo> queryProcTaskRuleDepartByWeb(ProcTaskRuleDepartPo po) throws Exception {
		Condition con = Condition.build("queryProcTaskRuleDepartByWeb").filter(po.convertThis2Map());
		List<ProcTaskRuleDepartPo> list = query(ProcTaskRuleDepartPo.class, con);
		return (list == null || list.isEmpty()) ? null : list;
	}

	@Override
	public int queryProcTaskRuleDepartCount(ProcTaskRuleDepartPo po) throws Exception {
		Condition con = Condition.build("queryProcTaskRuleDepartCount").filter(po.convertThis2Map());
		List<ProcTaskRuleDepartPo> list = query(ProcTaskRuleDepartPo.class, con);
		return (list == null || list.isEmpty()) ? 0 : list.size();
	}

}

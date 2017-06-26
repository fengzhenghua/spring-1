package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.ProcTacheRetPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcTacheRetServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcTacheRetServ")
public class ProcTacheRetServImpl extends BaseServImpl<ProcTacheRetPo> implements ProcTacheRetServ {

	@Override
	public List<ProcTacheRetPo> queryProcTacheRetByPo(ProcTacheRetPo po) throws Exception {
		Condition con = Condition.build("queryProcTacheRetByPo").filter(po.convertThis2Map());
		List<ProcTacheRetPo> list = query(ProcTacheRetPo.class, con);
		return (list == null || list.isEmpty()) ? null : list;
	}

}

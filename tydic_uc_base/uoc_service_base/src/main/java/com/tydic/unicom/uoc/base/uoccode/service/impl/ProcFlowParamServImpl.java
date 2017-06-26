package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.ProcFlowParamPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcFlowParamServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("ProcFlowParamServ")
public class ProcFlowParamServImpl extends BaseServImpl<ProcFlowParamPo> implements ProcFlowParamServ{

	@Override
	public List<ProcFlowParamPo> queryProcFlowParamByProcModCodeAndTacheCode(ProcFlowParamPo procFlowParamPo) throws Exception {
		Condition condition = Condition.build("queryProcFlowParamByProcModCodeAndTacheCode").filter(procFlowParamPo.convertThis2Map());
		List<ProcFlowParamPo> list = query(ProcFlowParamPo.class,condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}

package com.tydic.unicom.uoc.base.uoccode.service.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.base.uoccode.po.ProcFlowParamPo;

public interface ProcFlowParamServ {

	/**
	 * 根据proc_mod_code与tache_code查询
	 * */
	public List<ProcFlowParamPo> queryProcFlowParamByProcModCodeAndTacheCode(ProcFlowParamPo procFlowParamPo) throws Exception;
}

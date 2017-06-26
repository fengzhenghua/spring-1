package com.tydic.unicom.uoc.service.activiti.interfaces;

import java.util.List;

import com.tydic.unicom.uoc.service.activiti.vo.ProcFlowParamVo;

public interface ProcFlowParamServDu {

	public List<ProcFlowParamVo> queryProcFlowParamByProcModCodeAndTacheCode(ProcFlowParamVo procFlowParamVo) throws Exception;
}

package com.tydic.unicom.uoc.service.activiti.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.uoccode.po.ProcFlowParamPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.ProcFlowParamServ;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcFlowParamServDu;
import com.tydic.unicom.uoc.service.activiti.vo.ProcFlowParamVo;

@Service("ProcFlowParamServDu")
public class ProcFlowParamServDuImpl implements ProcFlowParamServDu{

	Logger logger = Logger.getLogger(ProcFlowParamServDuImpl.class);
	
	@Autowired
	private ProcFlowParamServ procFlowParamServ;
	
	@Override
	public List<ProcFlowParamVo> queryProcFlowParamByProcModCodeAndTacheCode(ProcFlowParamVo procFlowParamVo) throws Exception {
		ProcFlowParamPo procFlowParamPo = new ProcFlowParamPo();
		BeanUtils.copyProperties(procFlowParamVo, procFlowParamPo);
		List<ProcFlowParamPo> list = procFlowParamServ.queryProcFlowParamByProcModCodeAndTacheCode(procFlowParamPo);
		if(list != null && list.size()>0){
			List<ProcFlowParamVo> listOut = new ArrayList<ProcFlowParamVo>();
			for(int i=0;i<list.size();i++){
				ProcFlowParamVo procFlowParamVoOut = new ProcFlowParamVo();
				BeanUtils.copyProperties(list.get(i), procFlowParamVoOut);
				listOut.add(procFlowParamVoOut);
			}
			return listOut;
		}
		else{
			return null;
		}
	}

}

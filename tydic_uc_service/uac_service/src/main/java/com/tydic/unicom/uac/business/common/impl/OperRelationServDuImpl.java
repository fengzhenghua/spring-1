package com.tydic.unicom.uac.business.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uac.base.database.interfaces.OperRelationServ;
import com.tydic.unicom.uac.base.database.po.OperRelationPo;
import com.tydic.unicom.uac.business.common.interfaces.OperRelationServDu;
import com.tydic.unicom.uac.business.common.vo.OperRelationVo;

public class OperRelationServDuImpl implements OperRelationServDu{

	private static Logger logger = Logger.getLogger(OperRelationServDuImpl.class);
	
	@Autowired
	private OperRelationServ operRelationServ;
	
	@Override
	public List<OperRelationVo> queryOperRelationByUniformInfoOper(OperRelationVo operRelationVo){
		OperRelationPo operRelationPo = new OperRelationPo();
		BeanUtils.copyProperties(operRelationVo, operRelationPo);
		List<OperRelationPo> list = operRelationServ.queryOperRelationByUniformInfoOper(operRelationPo);
		if(list != null && list.size()>0){
			List<OperRelationVo> listResult = new ArrayList<OperRelationVo>();
			for(int i=0;i<list.size();i++){
				OperRelationVo operRelationVoOut = new OperRelationVo();
				BeanUtils.copyProperties(list.get(i), operRelationVoOut);
				listResult.add(operRelationVoOut);
			}
			return listResult;
		}
		else{
			return null;
		}
	}

}

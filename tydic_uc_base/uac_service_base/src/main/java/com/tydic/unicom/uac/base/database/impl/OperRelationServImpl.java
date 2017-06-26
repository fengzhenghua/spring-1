package com.tydic.unicom.uac.base.database.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uac.base.database.interfaces.OperRelationServ;
import com.tydic.unicom.uac.base.database.po.OperRelationPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("OperRelationServ")
public class OperRelationServImpl extends BaseServImpl<OperRelationPo> implements OperRelationServ{

	private static Logger logger = Logger.getLogger(OperRelationServImpl.class);
	
	@Override
	public List<OperRelationPo> queryOperRelationByUniformInfoOper(OperRelationPo operRelationPo){
		Map<String, Object> map = operRelationPo.convertThis2Map();
		Condition condition = getCondition("queryOperRelationByUniformInfoOper");
		condition.filter(map);
		List<OperRelationPo> list = query(OperRelationPo.class, condition);
		return list;
	}

}

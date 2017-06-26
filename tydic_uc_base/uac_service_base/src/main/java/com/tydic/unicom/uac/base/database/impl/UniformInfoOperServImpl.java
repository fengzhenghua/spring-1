package com.tydic.unicom.uac.base.database.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uac.base.database.interfaces.UniformInfoOperServ;
import com.tydic.unicom.uac.base.database.po.UniformInfoOperPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("UniformInfoOperServ")
public class UniformInfoOperServImpl extends BaseServImpl<UniformInfoOperPo> implements UniformInfoOperServ{

	@Override
	public UniformInfoOperPo uniformLoginIn(UniformInfoOperPo uniformInfoOperPo) throws Exception {
		Map<String, Object> map = uniformInfoOperPo.convertThis2Map();
		Condition condition = getCondition("queryUniformInfoOperLoginIn").filter(map);
		List<UniformInfoOperPo> list = query(UniformInfoOperPo.class, condition);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<UniformInfoOperPo> queryUniformInfoOperByUniformOperOrDeviceNumber(UniformInfoOperPo uniformInfoOper) {
		Map<String, Object> map = uniformInfoOper.convertThis2Map();
		Condition condition = getCondition("queryUniformInfoOper").filter(map);
		List<UniformInfoOperPo> list = query(UniformInfoOperPo.class, condition);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}

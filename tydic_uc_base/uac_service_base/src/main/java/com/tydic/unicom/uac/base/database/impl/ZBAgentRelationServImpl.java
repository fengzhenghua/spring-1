package com.tydic.unicom.uac.base.database.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uac.base.database.interfaces.ZBAgentRelationServ;
import com.tydic.unicom.uac.base.database.po.ZBInfoAgentRelationPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("ZBAgentRelationServ")
public class ZBAgentRelationServImpl extends BaseServImpl<ZBInfoAgentRelationPo> implements ZBAgentRelationServ{

	@Override
	public ZBInfoAgentRelationPo queryZBInfoAgentRelationByAgentNo(ZBInfoAgentRelationPo zbInfoAgentRelation) {
		Condition con = Condition.build("queryZBInfoAgentRelationByAgentNo").filter(zbInfoAgentRelation.convertThis2Map());
		List<ZBInfoAgentRelationPo> list = S.get(ZBInfoAgentRelationPo.class).query(con);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

}

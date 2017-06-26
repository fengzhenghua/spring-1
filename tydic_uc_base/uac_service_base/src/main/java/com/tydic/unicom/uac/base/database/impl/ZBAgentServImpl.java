package com.tydic.unicom.uac.base.database.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uac.base.database.interfaces.ZBAgentServ;
import com.tydic.unicom.uac.base.database.po.ZBInfoAgentPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("ZBAgentServ")
public class ZBAgentServImpl extends BaseServImpl<ZBInfoAgentPo> implements ZBAgentServ{

	@Override
	public ZBInfoAgentPo queryZBInfoAgentByChnlCode(ZBInfoAgentPo zbInfoAgent) {
		Condition con = Condition.build("queryZBInfoAgentByChnlCode").filter(zbInfoAgent.convertThis2Map());
		List<ZBInfoAgentPo> list = S.get(ZBInfoAgentPo.class).query(con);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public List<ZBInfoAgentPo> queryZBInfoAgentByChnlCodeOrNameOrRegionId(ZBInfoAgentPo zbInfoAgentPo) {
		Condition con = Condition.build("queryZBInfoAgentByChnlCodeOrNameOrRegionId").filter(zbInfoAgentPo.convertThis2Map());
		List<ZBInfoAgentPo> zbInfoAgentPos = S.get(ZBInfoAgentPo.class).query(con);
		return zbInfoAgentPos;
	}
}

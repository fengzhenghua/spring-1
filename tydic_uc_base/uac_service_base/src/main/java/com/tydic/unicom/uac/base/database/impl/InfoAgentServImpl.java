package com.tydic.unicom.uac.base.database.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uac.base.database.interfaces.InfoAgentServ;
import com.tydic.unicom.uac.base.database.po.InfoAgentPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoAgentServ")
public class InfoAgentServImpl extends BaseServImpl<InfoAgentPo> implements InfoAgentServ{

	@Override
	public List<InfoAgentPo> queryInfoAgentByDeptNo(InfoAgentPo infoAgentPo) {
		Condition con = Condition.build("queryInfoAgentByDeptNo").filter(infoAgentPo.convertThis2Map());
		List<InfoAgentPo> list = S.get(InfoAgentPo.class).query(con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}

package com.tydic.unicom.uac.base.database.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uac.base.database.interfaces.InfoAgentDevelopersServ;
import com.tydic.unicom.uac.base.database.po.InfoAgentDevelopersPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoAgentDevelopersServ")
public class InfoAgentDevelopersServImpl extends BaseServImpl<InfoAgentDevelopersPo> implements InfoAgentDevelopersServ{

	@Override
	public InfoAgentDevelopersPo queryInfoAgentDevelopersByDevCode(InfoAgentDevelopersPo infoAgentDevelopersPo) {
		return get(InfoAgentDevelopersPo.class,infoAgentDevelopersPo);
	}

	@Override
	public InfoAgentDevelopersPo queryInfoAgentDevelopersByOperNo(InfoAgentDevelopersPo infoAgentDevelopersPo) {
		Condition con = Condition.build("queryInfoAgentDevelopersByOperNo").filter(infoAgentDevelopersPo.convertThis2Map());
		List<InfoAgentDevelopersPo> agentDevelopers = S.get(InfoAgentDevelopersPo.class).query(con);
		if(agentDevelopers!=null && agentDevelopers.size()>0){
			return agentDevelopers.get(0);
		}else {
		    return null;
		}
	}
	
	@Override
	public List<InfoAgentDevelopersPo> queryInfoAgentDevelopers(
			InfoAgentDevelopersPo infoAgentDevelopersPo) {
		Condition con = Condition.build("queryInfoAgentDevelopers").filter(infoAgentDevelopersPo.convertThis2Map());
		List<InfoAgentDevelopersPo> agentDevelopers = S.get(InfoAgentDevelopersPo.class).query(con);
		return agentDevelopers;
	}
	
	@Override
	public List<InfoAgentDevelopersPo> queryInfoAgentDevelopersByCodeOrName(InfoAgentDevelopersPo infoAgentDevelopersPo){
		Condition con = Condition.build("queryInfoAgentDevelopersByCodeOrName").filter(infoAgentDevelopersPo.convertThis2Map());
		List<InfoAgentDevelopersPo> agentDevelopers = S.get(InfoAgentDevelopersPo.class).query(con);
		return agentDevelopers;
	}

}

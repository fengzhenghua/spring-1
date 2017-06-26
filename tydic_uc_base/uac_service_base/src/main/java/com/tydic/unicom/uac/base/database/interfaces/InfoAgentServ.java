package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.InfoAgentPo;

public interface InfoAgentServ {

	public List<InfoAgentPo> queryInfoAgentByDeptNo(InfoAgentPo infoAgentPo);
}

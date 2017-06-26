package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.InfoAuthorityPo;
import com.tydic.unicom.uac.base.database.po.InfoMenuPo;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;

public interface InfoMenuServ {

	public List<InfoMenuPo> queryMenuByOperId(InfoOperPo infoOperPo);
	
	public List<InfoMenuPo> queryMenuByInfoOper(InfoOperPo infoOperPo,InfoAuthorityPo infoAuthorityPo);
}

package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.base.database.po.InfoPagesMobilePo;

public interface InfoPagesMobileServ {

	public List<InfoPagesMobilePo> queryInfoPagesMobileByOperNo(InfoOperPo infoOperPo);
}

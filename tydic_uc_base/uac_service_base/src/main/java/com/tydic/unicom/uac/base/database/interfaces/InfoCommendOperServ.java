package com.tydic.unicom.uac.base.database.interfaces;

import java.util.List;

import com.tydic.unicom.uac.base.database.po.InfoCommendOperPo;

public interface InfoCommendOperServ {

	public List<InfoCommendOperPo> queryInfoCommendOperByOperNo(InfoCommendOperPo InfoCommendOperPo);

	public List<InfoCommendOperPo> queryInfoCommendOperByDevelopNo(InfoCommendOperPo InfoCommendOperPo);
}

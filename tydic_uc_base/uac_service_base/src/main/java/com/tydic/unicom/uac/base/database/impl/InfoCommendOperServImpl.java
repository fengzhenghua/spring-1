package com.tydic.unicom.uac.base.database.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uac.base.database.interfaces.InfoCommendOperServ;
import com.tydic.unicom.uac.base.database.po.InfoCommendOperPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoCommendOperServ")
public class InfoCommendOperServImpl extends BaseServImpl<InfoCommendOperPo> implements InfoCommendOperServ{

	@Override
	public List<InfoCommendOperPo> queryInfoCommendOperByOperNo(InfoCommendOperPo InfoCommendOperPo) {
		Condition condition = getCondition("queryInfoCommendOperByOperNo").filter(InfoCommendOperPo.convertThis2Map());
		List<InfoCommendOperPo> list = query(InfoCommendOperPo.class, condition);
		return list != null && list.size() > 0 ? list : null;
	}

	@Override
	public List<InfoCommendOperPo> queryInfoCommendOperByDevelopNo(InfoCommendOperPo InfoCommendOperPo) {
		Condition condition = getCondition("queryInfoCommendOperByDevelopNo").filter(InfoCommendOperPo.convertThis2Map());
		List<InfoCommendOperPo> list = query(InfoCommendOperPo.class, condition);
		return list != null && list.size() > 0 ? list : null;
	}

}

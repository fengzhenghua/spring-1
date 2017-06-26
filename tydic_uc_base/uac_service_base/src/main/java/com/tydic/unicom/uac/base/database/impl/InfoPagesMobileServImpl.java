package com.tydic.unicom.uac.base.database.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uac.base.database.interfaces.InfoPagesMobileServ;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.uac.base.database.po.InfoPagesMobilePo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoPagesMobileServ")
public class InfoPagesMobileServImpl extends BaseServImpl<InfoPagesMobilePo> implements InfoPagesMobileServ{

	@Override
	public List<InfoPagesMobilePo> queryInfoPagesMobileByOperNo(InfoOperPo infoOperPo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oper_id", infoOperPo.getOper_id());
		Condition con = Condition.build("queryInfoPagesMobileByOperId").filter(map);
		List<InfoPagesMobilePo> list = query(InfoPagesMobilePo.class, con);
		return list;
	}

}

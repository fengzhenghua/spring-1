package com.tydic.unicom.upc.base.database.code.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.base.database.code.interfaces.InfoBusiPayRelationServ;
import com.tydic.unicom.upc.base.database.po.code.InfoBusiPayRelationPo;

@Service("InfoBusiPayRelationServ")
public class InfoBusiPayRelationServImpl implements InfoBusiPayRelationServ {

	@Override
	public List<InfoBusiPayRelationPo> queryByBusiId(String busi_id) {
		Map<String, Object> params = new HashMap<>();
		params.put("busi_id", busi_id);
		
		Condition condition = Condition.build("queryByBusiId").filter(params);
		return S.get(InfoBusiPayRelationPo.class).query(condition);
	}

	@Override
	public InfoBusiPayRelationPo queryByBusIdPayType(String busi_id, String pay_type) {
		Map<String, Object> params = new HashMap<>();
		params.put("busi_id", busi_id);
		params.put("pay_type", pay_type);
		
		Condition condition = Condition.build("queryByBusIdPayType").filter(params);
		return S.get(InfoBusiPayRelationPo.class).queryFirst(condition);
	}

}

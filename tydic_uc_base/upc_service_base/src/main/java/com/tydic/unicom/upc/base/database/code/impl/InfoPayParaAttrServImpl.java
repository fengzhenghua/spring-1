package com.tydic.unicom.upc.base.database.code.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.base.database.code.interfaces.InfoPayParaAttrServ;
import com.tydic.unicom.upc.base.database.po.code.InfoPayParaAttrPo;

@Service("InfoPayParaAttrServ")
public class InfoPayParaAttrServImpl implements InfoPayParaAttrServ {

	@Override
	public List<InfoPayParaAttrPo> getPayParaByPayType(String busi_id, String pay_type) {
		
		if(busi_id == null || busi_id.equals("")){
			throw new IllegalArgumentException("找不到busi_id的值!");
		}
		if(pay_type == null || pay_type.equals("")){
			throw new IllegalArgumentException("找不到pay_type的值!");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("busi_id", busi_id);
		params.put("pay_type", pay_type);
		
		Condition condition = Condition.build("getPayParaByPayType").filter(params);
		return S.get(InfoPayParaAttrPo.class).query(condition);
	}

}

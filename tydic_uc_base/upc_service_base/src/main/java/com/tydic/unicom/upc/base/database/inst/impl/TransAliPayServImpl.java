package com.tydic.unicom.upc.base.database.inst.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.base.database.inst.interfaces.TransAliPayServ;
import com.tydic.unicom.upc.base.database.po.inst.TransAliPayPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("TransAliPayServ")
public class TransAliPayServImpl extends BaseServImpl<TransAliPayPo> implements TransAliPayServ {

	@Override
	public void addTrans(List<TransAliPayPo> poList) {
		for(TransAliPayPo po : poList){
			create(TransAliPayPo.class, po);
		}

	}

	@Override
	public int getCountTransByBillDate(String billDate, String appid) {
		
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("bill_date", billDate);
		paramsMap.put("appid", appid);
		
		Condition condition = Condition.build("getCountTransByBillDate").filter(paramsMap);
		return S.get(TransAliPayPo.class).queryFor().queryForInt(condition);
	}

}

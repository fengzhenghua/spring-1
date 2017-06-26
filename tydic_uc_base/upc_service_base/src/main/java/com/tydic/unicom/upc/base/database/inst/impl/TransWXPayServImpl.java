package com.tydic.unicom.upc.base.database.inst.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.base.database.inst.interfaces.TransWXPayServ;
import com.tydic.unicom.upc.base.database.po.inst.TransWXPayPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("TransWXPayServ")
public class TransWXPayServImpl extends BaseServImpl<TransWXPayPo> implements TransWXPayServ {

	@Override
	public void addTransWXPay(List<TransWXPayPo> poList) {
		
		for(TransWXPayPo po : poList){
			create(TransWXPayPo.class, po);
		}
	}

	@Override
	public int getCountTransByBillDate(String billDate, String appid, String mchid) {
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("bill_date", billDate);
		paramsMap.put("appid", appid);
		paramsMap.put("mchid", mchid);
		Condition condition = Condition.build("getCountTransByBillDate").filter(paramsMap);
		return S.get(TransWXPayPo.class).queryFor().queryForInt(condition);
	}

}

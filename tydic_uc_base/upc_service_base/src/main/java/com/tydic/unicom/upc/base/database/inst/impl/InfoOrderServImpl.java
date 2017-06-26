package com.tydic.unicom.upc.base.database.inst.impl;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.base.database.inst.interfaces.InfoOrderServ;
import com.tydic.unicom.upc.base.database.po.inst.InfoOrderPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoOrderServ")
public class InfoOrderServImpl extends BaseServImpl<InfoOrderPo> implements InfoOrderServ {

	@Override
	public boolean addInfoOrder(InfoOrderPo infoOrderPo) {
		create(InfoOrderPo.class,infoOrderPo);
		return true;
	}

	@Override
	public boolean updateInfoOrder(InfoOrderPo infoOrderPo) {
		if(infoOrderPo.getOrder_id() == null || infoOrderPo.getOrder_id().equals("")){
			throw new IllegalArgumentException("找不到order_id的值!");
		}
		update(InfoOrderPo.class,infoOrderPo);
		return true;
	}

	@Override
	public InfoOrderPo queryInfoOrderByOutOrderId(InfoOrderPo infoOrderPo) {
		if(infoOrderPo.getBusi_id() == null || infoOrderPo.getBusi_id().equals("")){
			throw new IllegalArgumentException("找不到busi_id的值!");
		}
		if(infoOrderPo.getOut_order_id() == null || infoOrderPo.getOut_order_id().equals("")){
			throw new IllegalArgumentException("找不到out_order_id的值!");
		}
		Condition condition = Condition.build("queryInfoOrderByOutOrderId").filter(infoOrderPo.convertThis2Map());
		return S.get(InfoOrderPo.class).queryFirst(condition);
	}

	@Override
	public InfoOrderPo queryInfoOrderByOrderId(InfoOrderPo infoOrderPo) {
		if(infoOrderPo.getOrder_id() == null || infoOrderPo.getOrder_id().equals("")){
			throw new IllegalArgumentException("找不到order_id的值!");
		}
		
		Condition condition = Condition.build("queryInfoOrderByOrderId").filter(infoOrderPo.convertThis2Map());
		return S.get(InfoOrderPo.class).queryFirst(condition);
		
	}

	@Override
	public InfoOrderPo queryInfoOrderByPayOrderId(InfoOrderPo infoOrderPo) {
		if(infoOrderPo.getPay_order_id() == null || infoOrderPo.getPay_order_id().equals("")){
			throw new IllegalArgumentException("找不到pay_order_id的值!");
		}
		
		Condition condition = Condition.build("queryInfoOrderByPayOrderId").filter(infoOrderPo.convertThis2Map());
		return S.get(InfoOrderPo.class).queryFirst(condition);
	}

}

package com.tydic.unicom.upc.base.database.inst.impl;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.base.database.inst.interfaces.OrderPayTransServ;
import com.tydic.unicom.upc.base.database.po.inst.OrderPayTransPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("OrderPayTransServ")
public class OrderPayTransServImpl extends BaseServImpl<OrderPayTransPo> implements OrderPayTransServ {

	@Override
	public OrderPayTransPo queryByPayOrderId(OrderPayTransPo orderPayTransPo) {
		if(orderPayTransPo.getPay_order_id() == null || orderPayTransPo.getPay_order_id().equals("")){
			throw new IllegalArgumentException("找不到pay_order_id的值!");
		}
		
		Condition condition = Condition.build("queryByPayOrderId").filter(orderPayTransPo.convertThis2Map());
		return S.get(OrderPayTransPo.class).queryFirst(condition);
	}

	@Override
	public boolean addOrderPayTrans(OrderPayTransPo orderPayTransPo) {
		create(OrderPayTransPo.class,orderPayTransPo);
		return true;
	}

	@Override
	public boolean updateOrderPayTrans(OrderPayTransPo orderPayTransPo) {
		if(orderPayTransPo.getPay_order_id() == null || orderPayTransPo.getPay_order_id().equals("")){
			throw new IllegalArgumentException("找不到pay_order_id的值!");
		}
		update(OrderPayTransPo.class,orderPayTransPo);
		return true;
	}

}

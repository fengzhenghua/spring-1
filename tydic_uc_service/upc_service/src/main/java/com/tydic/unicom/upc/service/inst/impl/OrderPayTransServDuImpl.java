package com.tydic.unicom.upc.service.inst.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.service.sequence.service.interfaces.SequenceServ;
import com.tydic.unicom.upc.base.database.inst.interfaces.OrderPayTransServ;
import com.tydic.unicom.upc.base.database.po.inst.OrderPayTransPo;
import com.tydic.unicom.upc.service.inst.interfaces.OrderPayTransServDu;
import com.tydic.unicom.upc.vo.inst.OrderPayTransVo;

public class OrderPayTransServDuImpl implements OrderPayTransServDu {

	@Autowired
	private OrderPayTransServ orderPayTransServ;
	
	@Autowired
	private SequenceServ sequenceServ;
	
	@Override
	public String addOrderPayTrans(OrderPayTransVo orderPayTransVo) {
		if(orderPayTransVo.getPay_order_id() == null || orderPayTransVo.getPay_order_id().equals("")){
			orderPayTransVo.setPay_order_id(genPayOrderId());
		}
		OrderPayTransPo orderPayTransPo = new OrderPayTransPo();
		BeanUtils.copyProperties(orderPayTransVo, orderPayTransPo);
		
		orderPayTransServ.addOrderPayTrans(orderPayTransPo);
		
		return orderPayTransVo.getPay_order_id();
	}

	@Override
	public boolean updateOrderPayTrans(OrderPayTransVo orderPayTransVo) {
		OrderPayTransPo orderPayTransPo = new OrderPayTransPo();
		BeanUtils.copyProperties(orderPayTransVo, orderPayTransPo);
		return orderPayTransServ.updateOrderPayTrans(orderPayTransPo);
	}

	@Override
	public OrderPayTransVo queryByPayOrderId(OrderPayTransVo orderPayTransVo) {
		OrderPayTransPo orderPayTransPo = new OrderPayTransPo();
		BeanUtils.copyProperties(orderPayTransVo, orderPayTransPo);
		OrderPayTransPo po = orderPayTransServ.queryByPayOrderId(orderPayTransPo);
		if(po != null){
			OrderPayTransVo vo = new OrderPayTransVo();
			BeanUtils.copyProperties(po, vo);
			return vo;
		}
		else{
			return null;
		}
	}

	
	/**
	 * 生成订单号
	 * @return
	 */
	private synchronized String genPayOrderId(){
		long seq = sequenceServ.genSequence("pay_order");
		
		String datetime = new SimpleDateFormat("HHmmss").format(new Date());
		
		Random random = new Random();
		int ran = random.nextInt(10000000) + 10000000;
		
		return datetime + seq + ran;
		
	}
}

package com.tydic.unicom.apc.base.order.interfaces;

import com.tydic.unicom.apc.base.order.po.InfoOrderPo;

public interface InfoOrderServ {

	/**
	 * 插入订单表（单条数据）
	 * */
	public boolean create(InfoOrderPo infoOrderPo) throws Exception;
	
	/**
	 * 根据订单号查询
	 * */
	public InfoOrderPo queryInfoOrderByOrderId(InfoOrderPo infoOrderPo) throws Exception;
	
	/**
	 * 更新订单数据
	 * */
	public boolean update(InfoOrderPo infoOrderPo) throws Exception;
}

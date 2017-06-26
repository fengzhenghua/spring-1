package com.tydic.unicom.upc.base.database.inst.interfaces;

import com.tydic.unicom.upc.base.database.po.inst.InfoOrderPo;

public interface InfoOrderServ {

	/**
	 * 新增
	 * @param infoOrderPo
	 * @return
	 */
	public boolean addInfoOrder(InfoOrderPo infoOrderPo);
	
	/**
	 * 更新order_id的数据
	 * @param infoOrderPo
	 * @return
	 */
	public boolean updateInfoOrder(InfoOrderPo infoOrderPo);
	
	/**
	 * 根据order_id查询数据
	 * @param infoOrderPo
	 * @return
	 */
	public InfoOrderPo queryInfoOrderByOrderId(InfoOrderPo infoOrderPo);
	
	/**
	 * 根据busi_id, out_order_id查询数据
	 * @param infoOrderPo
	 * @return
	 */
	public InfoOrderPo queryInfoOrderByOutOrderId(InfoOrderPo infoOrderPo);
	
	/**
	 * 根据pay_order_id查询数据
	 * @param infoOrderPo
	 * @return
	 */
	public InfoOrderPo queryInfoOrderByPayOrderId(InfoOrderPo infoOrderPo);
}

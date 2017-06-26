package com.tydic.unicom.upc.service.inst.interfaces;

import com.tydic.unicom.upc.vo.inst.InfoOrderVo;

public interface InfoOrderServDu {

	/**
	 * 新增
	 * @param infoOrderVo
	 * @return
	 */
	public String addInfoOrder(InfoOrderVo infoOrderVo);
	
	/**
	 * 更新order_id的数据
	 * @param infoOrderVo
	 * @return
	 */
	public boolean updateInfoOrder(InfoOrderVo infoOrderVo);
	
	/**
	 * 根据order_id查询数据
	 * @param infoOrderVo
	 * @return
	 */
	public InfoOrderVo queryInfoOrderByOrderId(InfoOrderVo infoOrderVo);
	
	/**
	 * 根据busi_id, out_order_id查询数据
	 * @param infoOrderVo
	 * @return
	 */
	public InfoOrderVo queryInfoOrderByOutOrderId(InfoOrderVo infoOrderVo);
	
	/**
	 * 根据pay_order_id查询数据
	 * @param infoOrderVo
	 * @return
	 */
	public InfoOrderVo queryInfoOrderByPayOrderId(InfoOrderVo infoOrderVo);
	
}

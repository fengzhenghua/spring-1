package com.tydic.unicom.upc.base.database.code.interfaces;

import java.util.List;

import com.tydic.unicom.upc.base.database.po.code.InfoBusiPayRelationPo;

public interface InfoBusiPayRelationServ {

	/**
	 * 根据busi_id获取支付方式权限配置
	 * @param busi_id
	 * @return
	 */
	public List<InfoBusiPayRelationPo> queryByBusiId(String busi_id);
	
	/**
	 * 根据busi_id和支付类型获取支付方式权限配置
	 * @param busi_id
	 * @param pay_type
	 * @return
	 */
	public InfoBusiPayRelationPo queryByBusIdPayType(String busi_id, String pay_type);
}

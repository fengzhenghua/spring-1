package com.tydic.unicom.upc.service.code.interfaces;

import java.util.List;

import com.tydic.unicom.upc.vo.code.InfoBusiPayRelationVo;


public interface InfoBusiPayRelationServDu {

	/**
	 * 根据busi_id获取支付方式权限配置
	 * @param busi_id
	 * @return
	 */
	public List<InfoBusiPayRelationVo> queryByBusiId(String busi_id);
	
	/**
	 * 根据busi_id和支付类型获取支付方式权限配置
	 * @param busi_id
	 * @param pay_type
	 * @return
	 */
	public InfoBusiPayRelationVo queryByBusIdPayType(String busi_id, String pay_type);
}

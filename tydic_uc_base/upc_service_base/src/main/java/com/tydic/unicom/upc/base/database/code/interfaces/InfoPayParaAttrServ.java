package com.tydic.unicom.upc.base.database.code.interfaces;

import java.util.List;

import com.tydic.unicom.upc.base.database.po.code.InfoPayParaAttrPo;

public interface InfoPayParaAttrServ {

	/**
	 * 根据支付类型，获取支付参数
	 * @param busi_id
	 * @param pay_type
	 * @return
	 */
	List<InfoPayParaAttrPo> getPayParaByPayType(String busi_id, String pay_type);
}

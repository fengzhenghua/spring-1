package com.tydic.unicom.upc.service.code.interfaces;

import java.util.List;

import com.tydic.unicom.upc.vo.code.InfoPayParaAttrValueVo;
import com.tydic.unicom.upc.vo.code.InfoPayParaAttrVo;


public interface InfoPayParaAttrServDu {

	/**
	 * 根据支付类型，获取支付参数
	 * @param busi_id
	 * @param pay_type
	 * @return
	 */
	public List<InfoPayParaAttrVo> getPayParaByPayType(String busi_id, String pay_type);
	
	/**
	 * 获取到支付类型的值，再转成对象
	 * @param busi_id
	 * @param pay_type
	 * @return
	 */
	public InfoPayParaAttrValueVo getPayParaValueByPayType(String busi_id, String pay_type);
}

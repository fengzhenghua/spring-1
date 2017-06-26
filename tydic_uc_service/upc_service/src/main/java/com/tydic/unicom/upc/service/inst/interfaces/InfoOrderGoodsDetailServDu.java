package com.tydic.unicom.upc.service.inst.interfaces;

import java.util.List;

import com.tydic.unicom.upc.vo.inst.InfoOrderGoodsDetailVo;

public interface InfoOrderGoodsDetailServDu {

	/**
	 * 添加明细
	 * @param infoOrderGoodsDetailVoList
	 */
	public void addInfoOrderGoodsDetail(List<InfoOrderGoodsDetailVo> infoOrderGoodsDetailVoList) throws Exception;
	
	/**
	 * 获取支付明细
	 * @param infoOrderGoodsDetailPo
	 * @return
	 */
	public List<InfoOrderGoodsDetailVo> queryGoodsDetailByOrderId(InfoOrderGoodsDetailVo infoOrderGoodsDetailVo) throws Exception;
}

package com.tydic.unicom.upc.base.database.inst.interfaces;

import java.util.List;

import com.tydic.unicom.upc.base.database.po.inst.InfoOrderGoodsDetailPo;

public interface InfoOrderGoodsDetailServ {

	/**
	 * 添加明细
	 * @param infoOrderGoodsDetailPo
	 */
	public void addInfoOrderGoodsDetail(InfoOrderGoodsDetailPo infoOrderGoodsDetailPo);
	
	/**
	 * 获取支付明细
	 * @param infoOrderGoodsDetailPo
	 * @return
	 */
	public List<InfoOrderGoodsDetailPo> queryGoodsDetailByOrderId(InfoOrderGoodsDetailPo infoOrderGoodsDetailPo);
}

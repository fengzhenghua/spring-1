package com.tydic.unicom.ugc.base.dataBase.interfaces;

import java.util.List;

import com.tydic.unicom.ugc.base.dataBase.po.GoodsFeePo;

public interface GoodsFeeServ {
	
	/**
	 * 根据商品ID 查询商品费用表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<GoodsFeePo> queryGoodsFeeByGoodsId(GoodsFeePo po) throws Exception;

}

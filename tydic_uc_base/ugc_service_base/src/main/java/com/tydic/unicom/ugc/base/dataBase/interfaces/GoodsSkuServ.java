package com.tydic.unicom.ugc.base.dataBase.interfaces;

import java.util.List;

import com.tydic.unicom.ugc.base.dataBase.po.GoodsSkuPo;

public interface GoodsSkuServ {
	
	/**
	 * 根据商品ID查询商品sku表
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public List<GoodsSkuPo> queryGoodsSkuByGoodsId(GoodsSkuPo po) throws Exception;
	
	public boolean addGoodsSku(GoodsSkuPo po) throws Exception;
	
	public boolean deleteGoodsSku(GoodsSkuPo po) throws Exception;
	
	public boolean updateGoodsSku(GoodsSkuPo po) throws Exception;

}

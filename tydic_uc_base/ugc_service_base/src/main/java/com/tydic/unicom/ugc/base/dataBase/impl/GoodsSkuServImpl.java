package com.tydic.unicom.ugc.base.dataBase.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.ugc.base.dataBase.interfaces.GoodsSkuServ;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsSkuPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("GoodsSkuServ")
public class GoodsSkuServImpl extends BaseServImpl<GoodsSkuPo> implements GoodsSkuServ{

	private static Logger logger = Logger.getLogger(GoodsSkuServImpl.class);

	@Override
	public List<GoodsSkuPo> queryGoodsSkuByGoodsId(GoodsSkuPo po) throws Exception{

		logger.info("=========query goods_sku========");
		Condition condition = Condition.build("queryGoodsSkuByGoodsId").filter(po.convertThis2Map());
		List<GoodsSkuPo> list = query(GoodsSkuPo.class, condition);
		if(list != null && list.size()>0){
			return list;
		} else {
			return null;
		}
	}

	@Override
	public boolean addGoodsSku(GoodsSkuPo po) throws Exception {
		create(GoodsSkuPo.class,po);
		return true;
	}

	@Override
	public boolean deleteGoodsSku(GoodsSkuPo po) throws Exception {
		Condition condition = Condition.build("deleteGoodsSkuPoByGoodsId").filter(po.convertThis2Map());
		int i = S.get(GoodsSkuPo.class).remove(condition);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateGoodsSku(GoodsSkuPo po) throws Exception {
		update(GoodsSkuPo.class,po);
		return true;
	}


}

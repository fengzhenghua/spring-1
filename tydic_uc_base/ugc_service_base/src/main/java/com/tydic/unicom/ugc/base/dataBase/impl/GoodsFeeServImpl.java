package com.tydic.unicom.ugc.base.dataBase.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.ugc.base.dataBase.interfaces.GoodsFeeServ;
import com.tydic.unicom.ugc.base.dataBase.po.GoodsFeePo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("GoodsFeeServ")
public class GoodsFeeServImpl extends BaseServImpl<GoodsFeePo> implements GoodsFeeServ{
	
	private static Logger logger = Logger.getLogger(GoodsFeeServImpl.class);
	
	@Override
	public List<GoodsFeePo> queryGoodsFeeByGoodsId(GoodsFeePo po) throws Exception{
		logger.info("=========query goods_fee========");
		Condition condition = Condition.build("queryGoodsFeeByGoodsId").filter(po.convertThis2Map());
		List<GoodsFeePo> list = query(GoodsFeePo.class, condition);
		if(list != null && list.size()>0){
			return list;
		} else {
			return null;
		}
		
	}
	
	

}

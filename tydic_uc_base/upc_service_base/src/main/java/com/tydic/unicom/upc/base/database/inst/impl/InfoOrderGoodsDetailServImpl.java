package com.tydic.unicom.upc.base.database.inst.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.base.database.inst.interfaces.InfoOrderGoodsDetailServ;
import com.tydic.unicom.upc.base.database.po.inst.InfoOrderGoodsDetailPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoOrderGoodsDetailServ")
public class InfoOrderGoodsDetailServImpl extends BaseServImpl<InfoOrderGoodsDetailPo> implements InfoOrderGoodsDetailServ {

	@Override
	public void addInfoOrderGoodsDetail(InfoOrderGoodsDetailPo infoOrderGoodsDetailPo) {
		create(InfoOrderGoodsDetailPo.class,infoOrderGoodsDetailPo);

	}

	@Override
	public List<InfoOrderGoodsDetailPo> queryGoodsDetailByOrderId(InfoOrderGoodsDetailPo infoOrderGoodsDetailPo) {
		if(infoOrderGoodsDetailPo.getOrder_id() == null || infoOrderGoodsDetailPo.getOrder_id().equals("")){
			throw new IllegalArgumentException("order_id的值!");
		}
		
		Condition condition = Condition.build("queryGoodsDetailByOrderId").filter(infoOrderGoodsDetailPo.convertThis2Map());
		return S.get(InfoOrderGoodsDetailPo.class).query(condition);
	}

}

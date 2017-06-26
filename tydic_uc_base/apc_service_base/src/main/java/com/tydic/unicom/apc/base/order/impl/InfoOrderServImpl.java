package com.tydic.unicom.apc.base.order.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.apc.base.order.interfaces.InfoOrderServ;
import com.tydic.unicom.apc.base.order.po.InfoOrderPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoOrderServ")
public class InfoOrderServImpl extends BaseServImpl<InfoOrderPo> implements InfoOrderServ{

	@Override
	public boolean create(InfoOrderPo infoOrderPo) throws Exception {
		create(InfoOrderPo.class,infoOrderPo);
		return true;
	}

	@Override
	public InfoOrderPo queryInfoOrderByOrderId(InfoOrderPo infoOrderPo) throws Exception {
		Condition con = Condition.build("queryInfoOrderByOrderId").filter(infoOrderPo.convertThis2Map());
		List<InfoOrderPo> list = query(InfoOrderPo.class, con);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public boolean update(InfoOrderPo infoOrderPo) throws Exception {
		update(InfoOrderPo.class,infoOrderPo);
		return true;
	}
	

}

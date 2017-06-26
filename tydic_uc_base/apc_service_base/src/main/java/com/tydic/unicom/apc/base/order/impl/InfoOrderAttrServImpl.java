package com.tydic.unicom.apc.base.order.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.apc.base.order.interfaces.InfoOrderAttrServ;
import com.tydic.unicom.apc.base.order.po.InfoOrderAttrPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoOrderAttrServ")
public class InfoOrderAttrServImpl extends BaseServImpl<InfoOrderAttrPo> implements InfoOrderAttrServ{

	@Override
	public boolean create(InfoOrderAttrPo infoOrderAttrPo) throws Exception {
		create(InfoOrderAttrPo.class,infoOrderAttrPo);
		return true;
	}

	@Override
	public boolean update(InfoOrderAttrPo infoOrderAttrPo)throws Exception {
		update(InfoOrderAttrPo.class,infoOrderAttrPo);
		return true;
	}

	@Override
	public InfoOrderAttrPo queryInfoOrderAttrByAttrIdAndOrderId(InfoOrderAttrPo InfoOrderAttrPo) throws Exception {
		Condition con = Condition.build("queryInfoOrderAttrByAttrIdAndOrderId").filter(InfoOrderAttrPo.convertThis2Map());
		List<InfoOrderAttrPo> list = query(InfoOrderAttrPo.class, con);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public InfoOrderAttrPo queryInfoOrderAttrByAttrIdAndAttrValue(InfoOrderAttrPo InfoOrderAttrPo) throws Exception {
		Condition con = Condition.build("queryInfoOrderAttrByAttrIdAndAttrValue").filter(InfoOrderAttrPo.convertThis2Map());
		List<InfoOrderAttrPo> list = query(InfoOrderAttrPo.class, con);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		else{
			return null;
		}
	}

	@Override
	public List<InfoOrderAttrPo> queryInfoOrderAttrByOrderId(InfoOrderAttrPo InfoOrderAttrPo) throws Exception {
		Condition con = Condition.build("queryInfoOrderAttrByOrderId").filter(InfoOrderAttrPo.convertThis2Map());
		List<InfoOrderAttrPo> list = query(InfoOrderAttrPo.class, con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}


}

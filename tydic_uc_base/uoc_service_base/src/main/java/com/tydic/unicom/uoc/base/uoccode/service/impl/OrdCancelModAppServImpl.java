package com.tydic.unicom.uoc.base.uoccode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.uoccode.po.OrdCancelModAppPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdCancelModAppServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("OrdCancelModAppServ")
public class OrdCancelModAppServImpl extends BaseServImpl<OrdCancelModAppPo> implements OrdCancelModAppServ{

	@Override
	public OrdCancelModAppPo queryOrdCancelModApp(OrdCancelModAppPo po) throws Exception {
		Condition con = Condition.build("queryOrdCancelModByPo").filter(po.convertThis2Map());
		List<OrdCancelModAppPo> list = query(OrdCancelModAppPo.class, con);
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	@Override
	public List<OrdCancelModAppPo> queryOrdCancelModAppByPo(OrdCancelModAppPo ordCancelModAppPo) throws Exception {
		Condition con = Condition.build("queryOrdCancelModByPo").filter(ordCancelModAppPo.convertThis2Map());
		List<OrdCancelModAppPo> list = query(OrdCancelModAppPo.class, con);
		return (list != null && list.size() > 0) ? list : null;
	}

}

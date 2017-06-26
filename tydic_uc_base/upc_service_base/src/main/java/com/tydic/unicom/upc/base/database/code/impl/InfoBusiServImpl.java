package com.tydic.unicom.upc.base.database.code.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.upc.base.database.code.interfaces.InfoBusiServ;
import com.tydic.unicom.upc.base.database.po.code.InfoBusiPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

/**
 * @author 吴川
 *
 */
@Service("InfoBusiServ")
public class InfoBusiServImpl extends BaseServImpl<InfoBusiPo> implements InfoBusiServ {

	@Override
	public InfoBusiPo queryByBusiId(InfoBusiPo infoBusiPo)  throws Exception {
		if(infoBusiPo.getBusi_id() == null || infoBusiPo.getBusi_id().equals("")){
			throw new IllegalArgumentException("找不到busi_id的值!");
		}
		Condition condition = Condition.build("queryByBusiId").filter(infoBusiPo.convertThis2Map());
		return S.get(InfoBusiPo.class).queryFirst(condition);
	}

	@Override
	public List<InfoBusiPo> queryAllInfoBusi() throws Exception {
		Condition condition = Condition.build("queryAllInfoBusi");
		return S.get(InfoBusiPo.class).query(condition);
	}

}

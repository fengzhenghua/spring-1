package com.tydic.unicom.uac.base.database.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uac.base.database.interfaces.InfoMenuServ;
import com.tydic.unicom.uac.base.database.po.InfoAuthorityPo;
import com.tydic.unicom.uac.base.database.po.InfoMenuPo;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoMenuServ")
public class InfoMenuServImpl extends BaseServImpl<InfoMenuPo> implements InfoMenuServ{

	@Override
	public List<InfoMenuPo> queryMenuByOperId(InfoOperPo infoOperPo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oper_id", infoOperPo.getOper_id());
		Condition con = Condition.build("queryInfoMenuByOperIDNew").filter(map);
		List<InfoMenuPo> list = query(InfoMenuPo.class, con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public List<InfoMenuPo> queryMenuByInfoOper(InfoOperPo infoOperPo,InfoAuthorityPo infoAuthorityPo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oper_id", infoOperPo.getOper_id());
		if(infoOperPo.getUni_oper_id()==null||infoOperPo.getUni_oper_id().equals("")){
			infoOperPo.setUni_oper_id("DefaultUniOper");
		}
		map.put("uni_oper_id", infoOperPo.getUni_oper_id());
		map.put("authority_id", infoAuthorityPo.getAuthority_id());
		Condition con = Condition.build("queryInfoMenuByOperID").filter(map);
		List<InfoMenuPo> list = query(InfoMenuPo.class, con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}

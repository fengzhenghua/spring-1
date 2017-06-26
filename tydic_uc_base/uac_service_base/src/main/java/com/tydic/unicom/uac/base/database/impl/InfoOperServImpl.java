package com.tydic.unicom.uac.base.database.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.uda.service.S;
import com.tydic.unicom.uac.base.database.interfaces.InfoOperServ;
import com.tydic.unicom.uac.base.database.po.InfoOperPo;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;
@Service("InfoOperServ")
public class InfoOperServImpl extends BaseServImpl<InfoOperPo> implements InfoOperServ{

	private static Logger logger = Logger.getLogger(InfoOperServImpl.class);
	
	@Override
	public List<InfoOperPo> queryInfoOperOperNo(InfoOperPo infoOperPo) throws Exception {
		Condition con = Condition.build("queryInfoOperOperNo").filter(infoOperPo.convertThis2Map());
		List<InfoOperPo> list = S.get(InfoOperPo.class).query(con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

	@Override
	public InfoOperPo getInfoOperByLoginCode(InfoOperPo infoOperPo) throws Exception {
		return get(InfoOperPo.class,infoOperPo);
	}

	@Override
	public List<InfoOperPo> queryInfoOperByOperNoAndOperName(String currDeptNo,String operNo, String operName) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("currDeptNo", currDeptNo);
		map.put("operNo", operNo);
		map.put("operName", operName);
		Condition con = Condition.build("queryInfoOperByOperNoAndOperName").filter(map);
		List<InfoOperPo> list = query(InfoOperPo.class,con);
		if(list != null && list.size()>0){
			return list;
		}
		else{
			return null;
		}
	}

}

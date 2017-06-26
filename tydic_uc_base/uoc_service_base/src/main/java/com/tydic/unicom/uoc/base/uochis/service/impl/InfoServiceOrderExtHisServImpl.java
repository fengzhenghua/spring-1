package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderExtHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderExtHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderExtHisServ")
public class InfoServiceOrderExtHisServImpl extends BaseServImpl<InfoServiceOrderExtHisPo> implements InfoServiceOrderExtHisServ{
	
	@Override
	public boolean create(InfoServiceOrderExtHisPo po) throws Exception{
		if(po==null){
			return false;
		}
		create(InfoServiceOrderExtHisPo.class,po);
		return true;
	}

	@Override
	public InfoServiceOrderExtHisPo queryInfoServiceOrderExtHisByServOrderNo(
			InfoServiceOrderExtHisPo po) throws Exception {
		
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getServ_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoServiceOrderExtHisByServOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderExtHisPo> list = query(InfoServiceOrderExtHisPo.class,con);      
		return (list.isEmpty()||list==null)?null:list.get(0);
	}

}

package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderExtHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderExtHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoSaleOrderExtHisServ")
public class InfoSaleOrderExtHisServImpl extends BaseServImpl<InfoSaleOrderExtHisPo> implements InfoSaleOrderExtHisServ{
	
	@Override
	public boolean create(InfoSaleOrderExtHisPo po) throws Exception{
		if(po==null){
			return false;
		}
		create(InfoSaleOrderExtHisPo.class,po);
		return true;
	}

	@Override
	public InfoSaleOrderExtHisPo queryInfoSaleOrderExtHisBySaleOrderNo(
			InfoSaleOrderExtHisPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoSaleOrderExtHisBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderExtHisPo> list = query(InfoSaleOrderExtHisPo.class,con);      
		return (list.isEmpty()||list==null)?null:list.get(0);
	}

}

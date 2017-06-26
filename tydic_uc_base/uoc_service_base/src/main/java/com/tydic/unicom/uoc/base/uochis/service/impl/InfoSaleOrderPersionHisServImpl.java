package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderPersionHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderPersionHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoSaleOrderPersionHisServ")
public class InfoSaleOrderPersionHisServImpl extends BaseServImpl<InfoSaleOrderPersionHisPo> implements InfoSaleOrderPersionHisServ{
	
	@Override
	public boolean createInfoSaleOrderPersionHisPo(InfoSaleOrderPersionHisPo po) throws Exception{
		
		if(po==null){
			return false;
		}
		create(InfoSaleOrderPersionHisPo.class,po);
		return true;
	}

	@Override
	public InfoSaleOrderPersionHisPo queryInfoSaleOrderPersionHisBySaleOrderNo(
			InfoSaleOrderPersionHisPo po) throws Exception {
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoSaleOrderPersionHisBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderPersionHisPo> list = query(InfoSaleOrderPersionHisPo.class,con); 
		return (list.isEmpty()||list==null)?null:list.get(0);
	}

}

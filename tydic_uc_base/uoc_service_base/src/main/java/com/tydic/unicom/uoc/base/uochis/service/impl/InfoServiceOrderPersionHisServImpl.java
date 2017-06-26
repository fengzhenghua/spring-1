package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderPersionHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderPersionHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoServiceOrderPersionHisServ")
public class InfoServiceOrderPersionHisServImpl extends BaseServImpl<InfoServiceOrderPersionHisPo> implements InfoServiceOrderPersionHisServ{
	
	@Override
	public boolean createInfoSaleOrderAttrHisPo(InfoServiceOrderPersionHisPo po)throws Exception{
		
		if(po==null){
			return false;
		}		
		create(InfoServiceOrderPersionHisPo.class,po);
		return true;
	}

	@Override
	public InfoServiceOrderPersionHisPo queryInfoServiceOrderPersionHisByOrderNo(
			InfoServiceOrderPersionHisPo po) throws Exception {
		String order_no="";
		if(po.getSale_order_no()!=null){
			order_no=po.getSale_order_no();
		}else if(po.getOfr_order_no()!=null){
			order_no=po.getOfr_order_no();
		}else if(po.getServ_order_no()!=null){
			order_no=po.getServ_order_no();
		}
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(order_no);
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoServiceOrderPersionHisByOrderNo").filter(po.convertThis2Map());
		List<InfoServiceOrderPersionHisPo> list = query(InfoServiceOrderPersionHisPo.class,con); 
		return (list.isEmpty()||list==null)?null:list.get(0);
		
	}

}

package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderEditHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderEditHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoSaleOrderEditHisServ")
public class InfoSaleOrderEditHisServImpl extends BaseServImpl<InfoSaleOrderEditHisPo> implements InfoSaleOrderEditHisServ{
	
	@Override
	public boolean createInfoSaleOrderEditHisPo(InfoSaleOrderEditHisPo po)throws Exception{
		
		if(po==null){
			return false;
		}
		
		create(InfoSaleOrderEditHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoSaleOrderEditHisPo> queryInfoSaleOrderEditHisByOrderNo(
			InfoSaleOrderEditHisPo po) throws Exception {
		
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoSaleOrderEditHisByOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderEditHisPo> list = query(InfoSaleOrderEditHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}

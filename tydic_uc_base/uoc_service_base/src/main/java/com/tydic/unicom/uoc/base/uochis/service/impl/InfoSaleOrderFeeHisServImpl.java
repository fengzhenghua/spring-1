package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderFeeHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderFeeHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoSaleOrderFeeHisServ")
public class InfoSaleOrderFeeHisServImpl extends BaseServImpl<InfoSaleOrderFeeHisPo> implements InfoSaleOrderFeeHisServ{
	
	@Override
	public boolean createInfoSaleOrderFeeHisPo(InfoSaleOrderFeeHisPo po)throws Exception{
		if(po==null){
			return false;
		}
		create(InfoSaleOrderFeeHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoSaleOrderFeeHisPo> queryInfoSaleOrderFeeHisBySaleOrderNo(
			InfoSaleOrderFeeHisPo po) throws Exception {
		
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoSaleOrderFeeHisBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderFeeHisPo> list = query(InfoSaleOrderFeeHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}

}

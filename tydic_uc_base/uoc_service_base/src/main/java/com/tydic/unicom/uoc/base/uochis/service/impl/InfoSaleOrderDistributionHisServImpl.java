package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderDistributionHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderDistributionHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoSaleOrderDistributionHisServ")
public class InfoSaleOrderDistributionHisServImpl extends BaseServImpl<InfoSaleOrderDistributionHisPo> implements InfoSaleOrderDistributionHisServ{
	
	@Override
	public boolean createInfoSaleOrderDistributionHisPo(InfoSaleOrderDistributionHisPo po) throws Exception{
		if(po==null){
			return false;
		}
		
		create(InfoSaleOrderDistributionHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoSaleOrderDistributionHisPo> queryInfoSaleOrderDistributionHisByOrderNo(
			InfoSaleOrderDistributionHisPo po) throws Exception {
		
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoSaleOrderDistributionHisByOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderDistributionHisPo> list = query(InfoSaleOrderDistributionHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	

}

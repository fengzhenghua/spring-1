package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderDistrDetailHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderDistrDetailHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoSaleOrderDistrDetailHisServ")
public class InfoSaleOrderDistrDetailHisServImpl  extends BaseServImpl<InfoSaleOrderDistrDetailHisPo> implements InfoSaleOrderDistrDetailHisServ{
	
	@Override
	public boolean createInfoSaleOrderDistrDetailHisPo(InfoSaleOrderDistrDetailHisPo po) throws Exception{
		if(po==null){
			return false;
		}
		
		create(InfoSaleOrderDistrDetailHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoSaleOrderDistrDetailHisPo> queryInfoSaleOrderDistrDetailHisByOrderNo(
			InfoSaleOrderDistrDetailHisPo po) throws Exception {
		
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoSaleOrderDistrDetailHisByOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderDistrDetailHisPo> list = query(InfoSaleOrderDistrDetailHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	

}

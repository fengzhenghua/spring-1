package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderOfrMapHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderOfrMapHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoSaleOrderOfrMapHisServ")
public class InfoSaleOrderOfrMapHisServImpl extends BaseServImpl<InfoSaleOrderOfrMapHisPo> implements InfoSaleOrderOfrMapHisServ{
	
	@Override
	public boolean createInfoSaleOrderOfrMapHisPo(InfoSaleOrderOfrMapHisPo po)throws Exception{
		if(po==null){
			return false;
		}		
		create(InfoSaleOrderOfrMapHisPo.class,po);
		return true;
	}

	@Override
	public List<InfoSaleOrderOfrMapHisPo> queryInfoSaleOrderOfrMapHisBySaleOrderNo(
			InfoSaleOrderOfrMapHisPo po) throws Exception {
		
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoSaleOrderOfrMapHisBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderOfrMapHisPo> list = query(InfoSaleOrderOfrMapHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public List<InfoSaleOrderOfrMapHisPo> queryInfoSaleOrderOfrMapHisByAccNbr(InfoSaleOrderOfrMapHisPo po)throws Exception{
		Condition con = Condition. build("queryInfoSaleOrderOfrMapHisByAccNbr").filter(po.convertThis2Map());
		List<InfoSaleOrderOfrMapHisPo> list = query(InfoSaleOrderOfrMapHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	

}
package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderServMapHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderServMapHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoSaleOrderServMapHisServ")
public class InfoSaleOrderServMapHisServImpl extends BaseServImpl<InfoSaleOrderServMapHisPo> implements InfoSaleOrderServMapHisServ{
	
	@Override
	public boolean createInfoSaleOrderServMapHisPo(InfoSaleOrderServMapHisPo po)throws Exception{
		if(po==null){
			return false;
		}		
		create(InfoSaleOrderServMapHisPo.class,po);
		return true;
		
	}

	@Override
	public List<InfoSaleOrderServMapHisPo> queryInfoSaleOrderServMapHisBySaleOrderNo(
			InfoSaleOrderServMapHisPo po) throws Exception {
		
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getSale_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoSaleOrderServMapHisBySaleOrderNo").filter(po.convertThis2Map());
		List<InfoSaleOrderServMapHisPo> list = query(InfoSaleOrderServMapHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	
	@Override
	public List<InfoSaleOrderServMapHisPo> queryInfoSaleOrderServMapHisByAccNbr(InfoSaleOrderServMapHisPo po)throws Exception{
		Condition con = Condition. build("queryInfoSaleOrderServMapHisByAccNbr").filter(po.convertThis2Map());
		List<InfoSaleOrderServMapHisPo> list = query(InfoSaleOrderServMapHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list ;
		}
		else{
			return null ;
		}
	}
	

}

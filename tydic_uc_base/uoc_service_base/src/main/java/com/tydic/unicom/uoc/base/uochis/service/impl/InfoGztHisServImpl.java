package com.tydic.unicom.uoc.base.uochis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uda.core.Condition;
import com.tydic.unicom.uoc.base.common.impl.StrUtil;
import com.tydic.unicom.uoc.base.uochis.po.InfoGztHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoGztHisServ;
import com.tydic.unicom.vdsBase.service.impl.BaseServImpl;

@Service("InfoGztHisServ")
public class InfoGztHisServImpl extends BaseServImpl<InfoGztHisPo> implements InfoGztHisServ{
	
	@Override
	public boolean createInfoGztHisPo(InfoGztHisPo po)throws Exception{
		if(po==null){
			return false;
		}
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getServ_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return false;
		}		
		create(InfoGztHisPo.class,po);
		return true;
	}
	
	@Override
	public InfoGztHisPo queryInfoGztHisByServOrderNo(InfoGztHisPo po)throws Exception{
		Map<String,String> strMap =StrUtil.splitStringFromOrderNo(po.getServ_order_no());
		if(strMap != null){
			po.setPart_month(strMap.get("part_month"));
			po.setArea_code(strMap.get("area_code"));
		}else{
			return null;
		}		
		Condition con = Condition. build("queryInfoGztHisByServOrderNo").filter(po.convertThis2Map());
		List<InfoGztHisPo> list = query(InfoGztHisPo.class,con);      
		if(list !=null&&list.size()>0){
			return list.get(0) ;
		}
		else{
			return null ;
		}
	}
	
	

}
